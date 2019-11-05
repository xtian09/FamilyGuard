package com.njdc.abb.familyguard.model.entity.http

class LoginRequest(
    var Method: String,
    var Usr: String,
    var Pwd: String? = null,
    var Phone: String? = null
)