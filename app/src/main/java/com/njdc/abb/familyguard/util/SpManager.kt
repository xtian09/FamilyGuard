package com.njdc.abb.familyguard.util

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.njdc.abb.familyguard.model.entity.data.*
import java.util.*


object SpManager {
    private lateinit var prefs: SharedPreferences
    private val homesMap by lazy { LinkedHashMap<String, Homes>() }
    private val roomMap by lazy { LinkedHashMap<String, Rooms>() }
    private val devicesMap by lazy { LinkedHashMap<String, Devices>() }
    private val smartHomesMap by lazy { LinkedHashMap<String, SmartHomes>() }
    val homeLiveData by lazy { BusMutableLiveData<Homes>() }
    val enviEntities by lazy { BusMutableLiveData<MutableList<EnvironmentEntity>>() }

    fun init(application: Application) {
        prefs = PreferenceManager.getDefaultSharedPreferences(application)
        val eList: MutableList<EnvironmentEntity>
        if (envirList.isNullOrEmpty()) {
            eList = EnvironmentType.values().map {
                return@map EnvironmentEntity(
                    it.mEnvironmentType,
                    it.mEnvironmentName,
                    it.mEnvironmentUnity,
                    it.mIconSelected,
                    it.mIconNormal,
                    it.mEnvironmentType != EnvironmentType.CO2.mEnvironmentType
                )
            }.toMutableList()
            envirList = eList
        } else {
            eList = arrayListOf()
            val typeList = EnvironmentType.values()
            for (e in envirList!!) {
                for (t in typeList) {
                    if (e.mEnvironmentType == t.mEnvironmentType) {
                        e.mEnvironmentName = t.mEnvironmentName
                        e.mEnvironmentUnity = t.mEnvironmentUnity
                        e.mIconSelected = t.mIconSelected
                        e.mIconNormal = t.mIconNormal
                    }
                }
                eList.add(e)
            }
        }
        enviEntities.set(eList)
    }

    fun initHomesData(homes: List<Homes>) {
        for (home in homes) {
            homesMap[home.HomeID] = home
            if (!home.Rooms.isNullOrEmpty()) {
                for (room in home.Rooms) {
                    roomMap[room.RoomID] = room
                    if (!room.Devices.isNullOrEmpty()) {
                        for (device in room.Devices!!) {
                            devicesMap[device.DeviceID] = device
                        }
                    }
                    if (!room.SmartHomes.isNullOrEmpty()) {
                        for (smartHome in room.SmartHomes!!) {
                            smartHomesMap[smartHome.SmartHomeID] = smartHome
                        }
                    }
                }
            }
        }
        home = if (homesMap.containsKey(homeId)) {
            homesMap[homeId]!!
        } else {
            // least have one home
            homesMap.entries.first().value
        }
        homeLiveData.set(home)
    }

    fun setEnviEntities(eList: MutableList<EnvironmentEntity>) {
        if (!eList.isNullOrEmpty()) {
            envirList = eList
        }
        enviEntities.postValue(eList)
    }

    var user: User?
        get() = Gson().fromJson(prefs.getString("user", ""), User::class.java)
        set(value) = prefs.edit().putString("user", Gson().toJson(value)).apply()

    private var homeId: String?
        get() = prefs.getString("homeID", "")
        set(value) = prefs.edit().putString("homeID", value).apply()

    private var roomId: String?
        get() = prefs.getString("roomID", "")
        set(value) = prefs.edit().putString("roomID", value).apply()

    private var deviceId: String?
        get() = prefs.getString("deviceID", "")
        set(value) = prefs.edit().putString("deviceID", value).apply()

    private var envirList: List<EnvironmentEntity>?
        get() {
            val type = object : TypeToken<List<EnvironmentEntity>>() {}.type
            return Gson().fromJson<List<EnvironmentEntity>>(
                prefs.getString("environmentList", ""),
                type
            )
        }
        set(value) = prefs.edit().putString("environmentList", Gson().toJson(value)).apply()

    private var home: Homes? = null
        set(value) {
            homeId = value?.HomeID
            field = value
            for (rooms in value?.Rooms!!) {
                if (!rooms.Devices.isNullOrEmpty()) {
                    room = rooms
                    return
                }
            }
            room = value.Rooms.firstOrNull()
        }

    private var room: Rooms? = null
        get() = if (roomMap.containsKey(roomId)) {
            roomMap[roomId]!!
        } else {
            // least have one room
            roomMap.entries.first().value
        }
        set(value) {
            roomId = value?.RoomID
            if (!value?.Devices.isNullOrEmpty()) {
                device = value?.Devices!!.first()
            }
            field = value
        }

    private var device: Devices? = null
        get() = if (devicesMap.containsKey(deviceId)) {
            devicesMap[deviceId]
        } else {
            deviceId = ""
            null
        }
        set(value) {
            deviceId = value?.DeviceID
            field = value
        }
}