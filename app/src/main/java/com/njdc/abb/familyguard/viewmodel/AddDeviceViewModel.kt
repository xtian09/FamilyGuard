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

    val wifiName = MutableLiveData<String>().init("")
    val passWord = MutableLiveData<String>().init("")
    val btnEnable = MutableLiveData<Boolean>().init(false)
    lateinit var qrTime: String

    init {
        Flowable.combineLatest(
            wifiName.toFlowable<String>(),
            passWord.toFlowable<String>(),
            BiFunction<String, String, Boolean> { _, _ ->
                return@BiFunction (!TextUtils.isEmpty(wifiName.get())
                        && !TextUtils.isEmpty(passWord.get()))
            }).doOnNext { btnEnable.set(it) }.subscribe()
    }

    fun getQRCode(): String {
        qrTime = SpManager.user!!.Usr.plus(System.currentTimeMillis())
        val jsonObject = JSONObject()
        try {
            jsonObject.put("s", wifiName.get())
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