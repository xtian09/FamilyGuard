package com.njdc.abb.familyguard.util

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.njdc.abb.familyguard.model.entity.data.User


object SpManager {
    lateinit var prefs: SharedPreferences

    fun init(application: Application) {
        prefs = PreferenceManager.getDefaultSharedPreferences(application)
    }

    var user: User?
        get() = Gson().fromJson(prefs.getString("user", ""), User::class.java)
        set(user) = prefs.edit().putString("user", Gson().toJson(user)).apply()
}