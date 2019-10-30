package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.util.init
import com.njdc.abb.familyguard.util.set
import com.njdc.abb.familyguard.util.toFlowable
import io.reactivex.Flowable
import io.reactivex.functions.Function3
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {

    private val REGEX_NAME = "[A-Za-z0-9]+"
    private val REGEX_PWD = "[A-Za-z0-9]+"
    private val REGEX_PHONE =
        "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$"

    private val userPattern = Pattern.compile(REGEX_NAME)
    private val pwdPattern = Pattern.compile(REGEX_PWD)
    private val phonePattern = Pattern.compile(REGEX_PHONE)


    val showLogin = MutableLiveData<Boolean>().init(true)
    val username = MutableLiveData<String>().init("")
    val password = MutableLiveData<String>().init("")
    val phone = MutableLiveData<String>().init("")
    val isBtnEnabled = MutableLiveData<Boolean>().init(false)

    init {
        Flowable.combineLatest(
            username.toFlowable<String>(),
            password.toFlowable<String>(),
            phone.toFlowable<String>(),
            Function3<String, String, String, Boolean> { t1, t2, t3 ->
                return@Function3 !(!userPattern.matcher(t2).matches()
                        || !pwdPattern.matcher(t1).matches() || !phonePattern.matcher(t3).matches())
            }).doOnNext { isBtnEnabled.set(it) }.subscribe()
    }

}