package com.njdc.abb.familyguard.model.entity.data

data class Rooms(
    var RoomID: String,
    var RoomName: String? = null,
    var RoomType: String? = null,
    var Devices: List<Devices>? = null,
    var SmartHomes: List<SmartHomes>? = null,
    var mRoomName: Int?,
    var mIconNormal: Int?,
    var mIconSelected: Int?
)