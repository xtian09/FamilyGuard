package com.njdc.abb.familyguard.model.entity.data

data class Homes(
    var HomeID: String,
    var HomeName: String? = null,
    var Owner: List<String>? = null,
    var Rooms: List<Rooms>,
    var NoOwner: NoOwner? = null
)