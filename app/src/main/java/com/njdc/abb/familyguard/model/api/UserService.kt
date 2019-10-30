package com.njdc.abb.familyguard.model.api

import com.njdc.abb.familyguard.model.entity.BaseResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("?Action=Login")
    fun login(@Query("Parameter") user: String): Single<BaseResponse>

    @GET("?Action=RegistUs&Parameter={user}")
    fun register(@Path("user") user: String): Single<BaseResponse>

    @GET("?Action=FindPwd&Parameter={user}")
    fun findPwd(@Path("user") user: String): Single<BaseResponse>
}