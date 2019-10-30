package com.njdc.abb.familyguard.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.njdc.abb.familyguard.viewmodel.LoginViewModel
import com.njdc.abb.familyguard.viewmodel.UserViewModel
import com.njdc.abb.familyguard.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class) //key
//    abstract fun bindMainViewModel(viewModel: MainViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}