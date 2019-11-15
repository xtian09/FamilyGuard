package com.njdc.abb.familyguard.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.util.get
import com.njdc.abb.familyguard.util.init
import com.njdc.abb.familyguard.util.set
import com.njdc.abb.familyguard.util.toFlowable
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class WifiViewModel @Inject constructor() : ViewModel() {

    val wifiName = MutableLiveData<String>().init("")
    val passWord = MutableLiveData<String>().init("")
    val btnEnable = MutableLiveData<Boolean>().init(false)

    init {
        Flowable.combineLatest(
            wifiName.toFlowable<String>(),
            passWord.toFlowable<String>(),
            BiFunction<String, String, Boolean> { _, _ ->
                return@BiFunction (!TextUtils.isEmpty(wifiName.get())
                        && !TextUtils.isEmpty(passWord.get()))
            }).doOnNext { btnEnable.set(it) }.subscribe()
    }
}