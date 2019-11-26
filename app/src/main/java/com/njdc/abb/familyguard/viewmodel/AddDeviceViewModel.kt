package com.njdc.abb.familyguard.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.util.*
import com.njdc.abb.familyguard.util.socket.SocketClient
import com.njdc.abb.familyguard.util.socket.SocketConfig
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class AddDeviceViewModel @Inject constructor() : ViewModel() {

    val wifiName by lazy { BusMutableLiveData<String>().init("") }
    val passWord by lazy { MutableLiveData<String>().init("") }
    val btnEnable by lazy { MutableLiveData<Boolean>().init(false) }
    val deviceName by lazy { MutableLiveData<String>().init("") }
    val roomName by lazy { MutableLiveData<String>().init("") }
    val btnAdEnable by lazy { MutableLiveData<Boolean>().init(false) }
    val qrWifiName by lazy { MutableLiveData<String>().init("") }
    private lateinit var qrTime: String

    init {
        Flowable.combineLatest(
            wifiName.toFlowable<String>(),
            passWord.toFlowable<String>(),
            BiFunction<String, String, Boolean> { _, _ ->
                return@BiFunction (!TextUtils.isEmpty(wifiName.get())
                        && !TextUtils.isEmpty(passWord.get()))
            }).doOnNext { btnEnable.set(it) }.subscribe()
        Flowable.combineLatest(
            deviceName.toFlowable<String>(),
            roomName.toFlowable<String>(),
            BiFunction<String, String, Boolean> { dName, rName ->
                return@BiFunction (!TextUtils.isEmpty(dName)
                        && !TextUtils.isEmpty(rName))
            }).doOnNext { btnAdEnable.set(it) }.subscribe()

    }

    fun getQRCode(): String {
        qrTime = SpManager.user!!.Usr.plus(System.currentTimeMillis())
        qrWifiName.set(wifiName.get())
        val jsonObject = JSONObject()
        try {
            jsonObject.put("s", qrWifiName.get())
            jsonObject.put("p", passWord.get())
            jsonObject.put("q", qrTime)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject.toString()
    }

    fun socketClient(): SocketClient {
        return SocketClient.create(
            SocketConfig.Builder().setIp("123.206.94.11").setPort(8443).setRequest(
                "QRCode-Send-QRID#".plus(qrTime).plus("*")
            ).build()
        )
    }

}