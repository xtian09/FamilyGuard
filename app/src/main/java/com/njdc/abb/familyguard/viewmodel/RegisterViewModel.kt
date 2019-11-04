package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.util.init
import com.njdc.abb.familyguard.util.toFlowable
import io.reactivex.Flowable
import io.reactivex.functions.Function3
import java.util.regex.Pattern
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val REGEX_NAME = "^[A-Za-z0-9]{8,16}$"
        private val REGEX_PWD = "^[A-Za-z0-9]{6,16}$"
        private val REGEX_PHONE =
            "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$"

        private val userPattern = Pattern.compile(REGEX_NAME)
        private val pwdPattern = Pattern.compile(REGEX_PWD)
        private val phonePattern = Pattern.compile(REGEX_PHONE)
    }

    val username = MutableLiveData<String>().init("")
    val password = MutableLiveData<String>().init("")
    val phone = MutableLiveData<String>().init("")
    var errorCheck: Flowable<String>? = null

    init {
        errorCheck = Flowable.combineLatest(
            username.toFlowable<String>(),
            password.toFlowable<String>(),
            phone.toFlowable<String>(),
            Function3<String, String, String, String> { t1, t2, t3 ->
                if (!userPattern.matcher(t1).matches()) {
                    return@Function3 "请填写8-16位字符用户名！"
                }
                if (!pwdPattern.matcher(t2).matches()) {
                    return@Function3 "请填写6-16位字符密码！"
                }
                if (!phonePattern.matcher(t3).matches()) {
                    return@Function3 "请填写11位手机号码！"
                }
                return@Function3 "success"
            })
    }

}