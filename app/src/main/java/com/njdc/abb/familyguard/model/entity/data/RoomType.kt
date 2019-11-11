package com.njdc.abb.familyguard.model.entity.data

import com.njdc.abb.familyguard.R

enum class RoomType constructor(
    val mRoomType: String,
    val mRoomName: Int,
    val mIconSelected: Int,
    val mIconNormal: Int
) {
    Living("SmartR000", R.string.room_living, R.mipmap.ic_living_enable, R.mipmap.ic_living_unable),
    Bedroom(
        "SmartR001",
        R.string.room_bedroom,
        R.mipmap.ic_bedroom_enable,
        R.mipmap.ic_bedroom_unable
    ),
    Kitchen(
        "SmartR002",
        R.string.room_kitchen,
        R.mipmap.ic_kitchen_enable,
        R.mipmap.ic_kitchen_unable
    ),
    Toilet("SmartR003", R.string.room_toilet, R.mipmap.ic_toilet_enable, R.mipmap.ic_toilet_unable),
    Dining("SmartR004", R.string.room_dining, R.mipmap.ic_dining_enable, R.mipmap.ic_dining_unable),
    Child("SmartR005", R.string.room_child, R.mipmap.ic_child_enable, R.mipmap.ic_child_unable),
    Baby("SmartR006", R.string.room_baby, R.mipmap.ic_baby_enable, R.mipmap.ic_baby_unable),
    Entertainment(
        "SmartR007",
        R.string.room_entertainment,
        R.mipmap.ic_entertainment_enable,
        R.mipmap.ic_entertainment_unable
    ),
    Media("SmartR008", R.string.room_media, R.mipmap.ic_media_enable, R.mipmap.ic_media_unable),
    Office("SmartR009", R.string.room_office, R.mipmap.ic_office_enable, R.mipmap.ic_office_unable),
    Gaming("SmartR010", R.string.room_gaming, R.mipmap.ic_gaming_enable, R.mipmap.ic_gaming_unable),
    Study("SmartR011", R.string.room_study, R.mipmap.ic_study_enable, R.mipmap.ic_study_unable),
    Workspace(
        "SmartR012",
        R.string.room_workspace,
        R.mipmap.ic_workspace_enable,
        R.mipmap.ic_workspace_unable
    ),
    Garden("SmartR013", R.string.room_garden, R.mipmap.ic_garden_enable, R.mipmap.ic_garden_unable),
    Cloak("SmartR014", R.string.room_cloak, R.mipmap.ic_cloak_enable, R.mipmap.ic_cloak_unable),
    Bathroom(
        "SmartR015",
        R.string.room_bathroom,
        R.mipmap.ic_bathroom_enable,
        R.mipmap.ic_bathroom_unable
    ),
    Laundry(
        "SmartR016",
        R.string.room_laundry,
        R.mipmap.ic_laundry_enable,
        R.mipmap.ic_laundry_unable
    ),
    Storage(
        "SmartR017",
        R.string.room_storage,
        R.mipmap.ic_storage_enable,
        R.mipmap.ic_storage_unable
    ),
    Loft("SmartR018", R.string.room_loft, R.mipmap.ic_loft_enable, R.mipmap.ic_loft_unable),
    Stair("SmartR019", R.string.room_stair, R.mipmap.ic_stair_enable, R.mipmap.ic_stair_unable),
    Add("SmartR020", R.string.room_add, R.mipmap.ic_add, R.mipmap.ic_add)
}
