package com.njdc.abb.familyguard.model.api

import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import com.njdc.abb.familyguard.model.entity.http.FindPwdResponse
import com.njdc.abb.familyguard.model.entity.http.HomeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("?Action=Login")
    fun login(@Query("Parameter") user: String): Single<BaseResponse<String>>

    @GET("?Action=RegistUsr")
    fun register(@Query("Parameter") user: String): Single<BaseResponse<String>>

    @GET("?Action=FindPwd")
    fun findPwd(@Query("Parameter") user: String): Single<FindPwdResponse<String>>

    @GET("?Action=GetAllHomeData")
    fun getAllHomeData(@Query("Parameter") user: String): Single<BaseResponse<HomeResponse>>
}