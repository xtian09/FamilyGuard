package com.njdc.abb.familyguard.model.entity.http

class FindPwdResponse<T>(
    Method: String?,
    Status: String?,
    ErrorCode: String?,
    Message: String?,
    Data: T?,
    var Pwd: String?
) : BaseResponse<T>(Method, Status, ErrorCode, Message, Data)
