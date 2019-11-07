package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.model.entity.UserSource
import com.njdc.abb.familyguard.model.entity.data.Devices
import com.njdc.abb.familyguard.model.entity.data.Homes
import com.njdc.abb.familyguard.model.entity.data.Rooms
import com.njdc.abb.familyguard.model.entity.data.User
import com.njdc.abb.familyguard.model.entity.http.HomeResponse
import com.njdc.abb.familyguard.model.entity.http.LoginRequest
import com.njdc.abb.familyguard.model.repository.UserRepository
import com.njdc.abb.familyguard.util.*
import io.reactivex.Single
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository) : ViewModel() {

    val user by lazy {
        MutableLiveData<UserSource<User>>().init(SpManager.user?.let {
            UserSource.authenticated(
                it
            )
        } ?: UserSource.logout())
    }

    val home by lazy {
        SpManager.home?.let { homes ->
            room.set(SpManager.room)
            device.set(SpManager.device)
            MutableLiveData<Homes>().init(homes)
        } ?: MutableLiveData()
    }

    val room by lazy { MutableLiveData<Rooms>() }

    val device by lazy { MutableLiveData<Devices>() }

    fun setUser(userOut: User) {
        SpManager.user = userOut
        user.set(UserSource.authenticated(userOut))
    }

    fun setHome(homes: Homes) {
        SpManager.home = homes
        home.set(SpManager.home)
    }

    fun setRoom(rooms: Rooms) {
        SpManager.room = rooms
        room.set(SpManager.room)
    }

    fun setDevice(devices: Devices) {
        SpManager.device = devices
        device.set(SpManager.device)
    }

    fun login(Usr: String, Pwd: String) = userRepository.login(
        LoginRequest(
            "Login",
            Usr,
            Pwd
        )
    ).async().handleResult()

    fun register(Usr: String, Pwd: String, Phone: String) = userRepository.register(
        LoginRequest(
            "RegistUsr",
            Usr,
            Pwd,
            Phone
        )
    ).async().handleResult()

    fun findPwd(Usr: String, Phone: String) = userRepository.findPwd(
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

    fun error(error: String) {
        user.set(UserSource.error(error))
    }

    fun getAllHomeData(Usr: String) = userRepository.getAllHomeData(
        LoginRequest(
            "GetAllHomeData",
            Usr
        )
    ).handleResult().flatMap {
        with(it.Data as HomeResponse) {
            if (Homes.isNullOrEmpty()) {
                return@flatMap Single.error<Boolean>(Throwable("list is null"))
            } else {
                SpManager.initData(Homes!!)
                return@flatMap Single.just(true)
            }
        }
    }.async()
}