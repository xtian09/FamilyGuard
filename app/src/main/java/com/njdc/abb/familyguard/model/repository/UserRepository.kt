package com.njdc.abb.familyguard.model.repository

import com.google.gson.Gson
import com.njdc.abb.familyguard.model.api.UserService
import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import com.njdc.abb.familyguard.model.entity.http.LoginRequest
import com.njdc.abb.familyguard.model.entity.http.RoomResponse
import io.reactivex.Single
import javax.inject.Inject


class UserRepository @Inject constructor(private val remote: UserService, private val gson: Gson) {

    fun login(loginRequest: LoginRequest): Single<BaseResponse<String>> =
        remote.login(gson.toJson(loginRequest))

    fun register(loginRequest: LoginRequest): Single<BaseResponse<String>> =
        remote.register(gson.toJson(loginRequest))

    fun findPwd(loginRequest: LoginRequest): Single<BaseResponse<String>> =
        remote.findPwd(gson.toJson(loginRequest))

    fun getAllHomeData(loginRequest: LoginRequest): Single<BaseResponse<RoomResponse>> =
        remote.getAllHomeData(gson.toJson(loginRequest))

}