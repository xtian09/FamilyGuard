package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep3Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.createQRCode
import com.njdc.abb.familyguard.util.getScreenWidth
import com.njdc.abb.familyguard.viewmodel.WifiViewModel

class AdStep3Fragment : BaseFragment<FrgAdStep3Binding>() {

    private val wifiViewModel by viewModels<WifiViewModel>({ requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_ad_step3

    override fun loadData() {
        mBinding.tbStep3.setLeftOnClickListener(View.OnClickListener { findNavController().popBackStack() })
        mBinding.ivQrCode.setImageBitmap(wifiViewModel.getQRCode().createQRCode(activity!!.getScreenWidth()))
        wifiViewModel.socketObservable().bindLifeCycle(this).subscribe({
            it.data?.let {
                var b = it

            }
        }, {

        })
    }
}