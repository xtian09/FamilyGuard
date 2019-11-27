package com.njdc.abb.familyguard.model.api

import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RoomService {

    @GET("?Action=NewAddDevice")
    fun addDevice(@Query("Parameter") device: String): Single<BaseResponse<String>>

}