package com.njdc.abb.familyguard.model.repository

import com.google.gson.Gson
import com.njdc.abb.familyguard.model.api.RoomService
import com.njdc.abb.familyguard.model.entity.http.AddDeviceRequest
import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class RoomRepository @Inject constructor(private val remote: RoomService, private val gson: Gson) {

    fun addDevice(addDeviceRequest: AddDeviceRequest): Single<BaseResponse<String>> =
        remote.addDevice(gson.toJson(addDeviceRequest))
}