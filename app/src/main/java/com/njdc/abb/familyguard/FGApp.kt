package com.njdc.abb.familyguard

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.njdc.abb.familyguard.di.DaggerAppComponent
import com.njdc.abb.familyguard.util.NetManager
import com.njdc.abb.familyguard.util.SpManager
import com.njdc.abb.familyguard.util.http.DefaultNetProvider
import javax.inject.Inject

class FGApp : Application() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        NetManager.registerProvider(DefaultNetProvider(this))
        SpManager.init(this)
        DaggerAppComponent.builder().app(this).build().inject(this)
    }
}