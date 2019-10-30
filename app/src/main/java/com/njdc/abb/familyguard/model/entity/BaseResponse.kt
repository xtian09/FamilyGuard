package com.njdc.abb.familyguard.model.entity

open class BaseResponse(
    var Method: String,
    var Status: String,
    var ErrorCode: String,
    var Message: String
)