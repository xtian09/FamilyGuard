package com.njdc.abb.familyguard.di

import android.app.Application
import com.njdc.abb.familyguard.FGApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ViewModelModule::class)])
interface AppComponent {

    fun inject(fgApp: FGApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(fgApp: Application): Builder

        fun build(): AppComponent
    }
}






