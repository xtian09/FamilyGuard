package com.njdc.abb.familyguard.model.entity.http

class AlterPwdRequest(
    Method: String,
    var Usr: String,
    var OldPwd: String,
    var NewPwd: String
) : BaseRequest(Method)