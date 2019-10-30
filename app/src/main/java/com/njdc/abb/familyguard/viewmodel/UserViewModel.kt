package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.model.entity.User
import com.njdc.abb.familyguard.model.repository.UserRepository
import com.njdc.abb.familyguard.util.SpManager
import com.njdc.abb.familyguard.util.async
import com.njdc.abb.familyguard.util.set
import javax.inject.Inject

class UserViewModel @Inject constructor(var userRepository: UserRepository) : ViewModel() {

    private var user = MutableLiveData<User>()

    fun getUser(): MutableLiveData<User> {
        SpManager.user?.let {
            user.set(it)
        }
        return user
    }

    fun login(user: User) = userRepository.login(user).async()

    fun register(user: User) = userRepository.register(user).async()

    fun findPwd(user: User) = userRepository.findPwd(user).async()
}