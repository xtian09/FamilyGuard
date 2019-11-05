package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.model.entity.UserSource
import com.njdc.abb.familyguard.model.entity.data.User
import com.njdc.abb.familyguard.model.entity.http.LoginRequest
import com.njdc.abb.familyguard.model.repository.UserRepository
import com.njdc.abb.familyguard.util.SpManager
import com.njdc.abb.familyguard.util.async
import com.njdc.abb.familyguard.util.handleResult
import com.njdc.abb.familyguard.util.set
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository) : ViewModel() {

    private var user = MutableLiveData<UserSource<User>>()

    init {
        SpManager.user?.let {
            user.set(UserSource.authenticated(it))
        } ?: user.set(UserSource.logout())
    }

    fun getUser(): MutableLiveData<UserSource<User>> {
        return user
    }

    fun setUser(userOut: User) {
        SpManager.user = userOut
        user.set(UserSource.authenticated(userOut))
    }

    fun login(
        Usr: String,
        Pwd: String
    ) = userRepository.login(
        LoginRequest(
            "Login",
            Usr,
            Pwd
        )
    ).async().handleResult()

    fun register(
        Usr: String,
        Pwd: String,
        Phone: String? = ""
    ) = userRepository.register(
        LoginRequest(
            "RegistUsr",
            Usr,
            Pwd,
            Phone
        )
    ).async().handleResult()

    fun findPwd(
        Usr: String,
        Phone: String
    ) = userRepository.findPwd(
        LoginRequest(
            "FindPwd",
            Usr,
            "",
            Phone
        )
    ).async().handleResult()

    fun logout() {
        SpManager.user = null
        user.set(UserSource.logout())
    }

    fun getAllHomeData(Usr: String) = userRepository.getAllHomeData(
        LoginRequest(
            "GetAllHomeData",
            Usr
        )
    ).async().handleResult()
}