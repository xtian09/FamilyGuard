package com.njdc.abb.familyguard.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.njdc.abb.familyguard.viewmodel.*
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

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FindPwdViewModel::class)
    abstract fun bindFindPwdViewModel(viewModel: FindPwdViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddDeviceViewModel::class)
    abstract fun bindWifiViewModel(viewModel: AddDeviceViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}