package com.njdc.abb.familyguard.model.entity.data

data class Homes(
    var HomeID: String? = null,
    var HomeName: String? = null,
    var Owner: List<String>? = null,
    var Rooms: List<Rooms>? = null,
    var NoOwner: NoOwner? = null
)