package com.njdc.abb.familyguard.model.entity.http

class LoginRequest(
    Method: String,
    var Usr: String,
    var Pwd: String? = null,
    var Phone: String? = null
) : BaseRequest(Method)