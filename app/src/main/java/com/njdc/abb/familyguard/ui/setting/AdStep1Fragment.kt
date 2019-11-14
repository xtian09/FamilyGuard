package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep1Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment

class AdStep1Fragment : BaseFragment<FrgAdStep1Binding>(), View.OnClickListener {

    override fun getLayoutId() = R.layout.frg_ad_step1

    override fun loadData() {
        mBinding.clickListener = this
        mBinding.tbStep1.setLeftOnClickListener(View.OnClickListener { activity!!.finish() })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_light_guide -> findNavController().navigate(R.id.action_adStep1Fragment_to_adLightFragment)
            R.id.btn_step2 -> findNavController().navigate(R.id.action_adStep1Fragment_to_adStep2Fragment)
        }
    }
}