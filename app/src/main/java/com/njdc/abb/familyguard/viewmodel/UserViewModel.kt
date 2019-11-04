package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.model.entity.User
import com.njdc.abb.familyguard.model.entity.UserSource
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
        Pwd: String,
        Phone: String? = ""
    ) = userRepository.login(User(Usr, Pwd, Phone, "Login")).async().handleResult()

    fun register(
        Usr: String,
        Pwd: String,
        Phone: String? = ""
    ) = userRepository.register(User(Usr, Pwd, Phone, "RegistUsr")).async().handleResult()

    fun findPwd(user: User) = userRepository.findPwd(user).async().handleResult()

    fun logout() {
        SpManager.user = null
        user.set(UserSource.logout())
    }

    fun getAllHomeData(user: User) = userRepository.getAllHomeData(user).async().handleResult()
}