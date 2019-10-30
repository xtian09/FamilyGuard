package com.njdc.abb.familyguard.model.repository

import com.google.gson.Gson
import com.njdc.abb.familyguard.model.api.UserService
import com.njdc.abb.familyguard.model.entity.BaseResponse
import com.njdc.abb.familyguard.model.entity.User
import io.reactivex.Single
import javax.inject.Inject


class UserRepository @Inject constructor(private val remote: UserService, private val gson: Gson) {

    fun login(user: User): Single<BaseResponse> = remote.login(gson.toJson(user))

    fun register(user: User): Single<BaseResponse> = remote.register(gson.toJson(user))

    fun findPwd(user: User): Single<BaseResponse> = remote.findPwd(gson.toJson(user))

}