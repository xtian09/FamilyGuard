package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdLightBinding
import com.njdc.abb.familyguard.ui.base.BaseFragment

class AdLightFragment : BaseFragment<FrgAdLightBinding>() {

    override fun getLayoutId() = R.layout.frg_ad_light

    override fun loadData() {
        mBinding.tbLight.setLeftOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })
    }

}