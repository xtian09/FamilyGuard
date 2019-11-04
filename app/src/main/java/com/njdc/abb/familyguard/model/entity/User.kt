package com.njdc.abb.familyguard.model.entity

data class User(
    var Usr: String,
    var Pwd: String,
    var Phone: String? = null,
    val Method: String? = null
)