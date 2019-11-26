package com.njdc.abb.familyguard.util

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.njdc.abb.familyguard.model.entity.data.*
import java.util.*

/**
 * 数据缓存信息，勿修改，可添加
 */
object SpManager {

    private lateinit var prefs: SharedPreferences
    // 所有家庭信息
    val homeListLiveData by lazy { BusMutableLiveData<List<Homes>>() }
    // 当前家庭
    val homeLiveData by lazy { BusMutableLiveData<Homes>() }
    // 当前房间
    val roomLiveData by lazy { BusMutableLiveData<Rooms>() }
    // 当前设备
    val deviceLiveData by lazy { BusMutableLiveData<Devices>() }
    // 当前环境参数
    val enviEntities by lazy { BusMutableLiveData<MutableList<EnvironmentEntity>>() }

    /**
     * 始化本地参数，用户数据、环境参数
     */
    fun init(application: Application) {
        prefs = PreferenceManager.getDefaultSharedPreferences(application)
        val eList: MutableList<EnvironmentEntity>
        // 环境参数无初始值时使用默认
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

    /**
     * 初始化家庭信息
     */
    fun initHomesData(homes: List<Homes>) {
        val homesMap = LinkedHashMap<String, Homes>()
        homeListLiveData.set(homes)
        for (home in homes) {
            homesMap[home.HomeID] = home
        }
        home = if (homesMap.containsKey(homeId)) {
            homesMap[homeId]!!
        } else {
            // least have one home
            homesMap.entries.first().value
        }
        homeLiveData.set(home)
        roomLiveData.set(room)
        deviceLiveData.set(device)
    }

    /**
     * 设置并保存环境参数
     */
    fun setEnviEntities(eList: MutableList<EnvironmentEntity>) {
        if (!eList.isNullOrEmpty()) {
            envirList = eList
        }
        enviEntities.postValue(eList)
    }

    /**
     * 设置并保存家庭
     */
    fun setHomeData(homes: Homes) {
        home = homes
        homeLiveData.set(homes)
    }

    /**
     * 设置并保存房间
     */
    fun setRoomData(rooms: Rooms) {
        room = rooms
        roomLiveData.set(rooms)
    }

    /**
     * 设置并保存设备
     */
    fun setDeviceData(devices: Devices) {
        device = devices
        deviceLiveData.set(devices)
    }

    /**
     * 清除所有数据
     */
    fun clearData() {
        user = null
        homeId = null
        envirList = null
        dustQuantity = false
        monitorDisplay = false
    }

    /**
     * 粉尘开关
     */
    var dustQuantity: Boolean
        get() = prefs.getBoolean("dust_quantity", false)
        set(value) = prefs.edit().putBoolean("dust_quantity", value).apply()

    /**
     * 保存线路图历史单元开关
     */
    var monitorDisplay: Boolean
        get() = prefs.getBoolean("monitor_display", false)
        set(value) = prefs.edit().putBoolean("monitor_display", value).apply()

    /**
     * 用户数据
     */
    var user: User?
        get() = Gson().fromJson(prefs.getString("user", ""), User::class.java)
        set(value) = prefs.edit().putString("user", Gson().toJson(value)).apply()

    /**
     * 用户当前家庭
     */
    private var homeId: String?
        get() = prefs.getString("homeID", "")
        set(value) = prefs.edit().putString("homeID", value).apply()

    /**
     * 用户当前配置环境参数
     */
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
            if (room == null) {
                for (rooms in value?.Rooms!!) {
                    if (!rooms.Devices.isNullOrEmpty()) {
                        room = rooms
                        return
                    }
                }
                room = value.Rooms.firstOrNull()
            }
        }

    private var room: Rooms? = null
        set(value) {
            if (!value?.Devices.isNullOrEmpty()) {
                device = value?.Devices!!.first()
            }
            field = value
        }

    private var device: Devices? = null
}