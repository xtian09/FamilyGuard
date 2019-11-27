package com.njdc.abb.familyguard.model.entity.http

class AddDeviceRequest(
    Method: String,
    var Usr: String,
    var HomeID: String,
    var RoomID: String,
    var DeviceID: String,
    var DeviceName: String,
    var ProductID: String?,
    var FWActivateCode: String
) : BaseRequest(Method)