package com.njdc.abb.familyguard.util

import java.util.regex.Pattern


object Constants {

    const val BASE_URL = "http://123.206.94.11/"

    private const val REGEX_NAME = "^[A-Za-z0-9]{8,16}$"
    private const val REGEX_PWD = "^[A-Za-z0-9]{6,16}$"
    private const val REGEX_PHONE =
        "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$"

    val userPattern by lazy { Pattern.compile(REGEX_NAME) }
    val pwdPattern by lazy { Pattern.compile(REGEX_PWD) }
    val phonePattern by lazy { Pattern.compile(REGEX_PHONE) }

}
