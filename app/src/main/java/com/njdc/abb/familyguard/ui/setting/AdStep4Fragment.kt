package com.njdc.abb.familyguard.ui.setting

import androidx.fragment.app.viewModels
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep4Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.viewmodel.AddDeviceViewModel
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class AdStep4Fragment : BaseFragment<FrgAdStep4Binding>() {

    private val wifiViewModel by viewModels<AddDeviceViewModel>({ requireActivity() }, { factory })
    private val userViewModel by viewModels<UserViewModel> { factory }

    override fun getLayoutId() = R.layout.frg_ad_step4

    override fun loadData() {
        mBinding.vm = wifiViewModel

    }
}