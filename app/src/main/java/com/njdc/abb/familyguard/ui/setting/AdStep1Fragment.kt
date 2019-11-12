package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep1Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment

class AdStep1Fragment : BaseFragment<FrgAdStep1Binding>(), View.OnClickListener {

    override fun getLayoutId() = R.layout.frg_ad_step1

    override fun loadData() {

    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.iv_2step2) {
            findNavController().navigate(R.id.action_adStep1Fragment_to_adStep2Fragment)
        }
    }

}