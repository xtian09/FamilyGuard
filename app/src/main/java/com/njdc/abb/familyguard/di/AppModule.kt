package com.njdc.abb.familyguard.di

import com.google.gson.Gson
import com.njdc.abb.familyguard.model.api.RoomService
import com.njdc.abb.familyguard.model.api.UserService
import com.njdc.abb.familyguard.util.Constants
import com.njdc.abb.familyguard.util.NetManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return NetManager.getRetrofit(Constants.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserService {
        return retrofit.create<UserService>(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomApi(retrofit: Retrofit): RoomService {
        return retrofit.create<RoomService>(RoomService::class.java)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}