package com.njdc.abb.familyguard.ui.setting

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.AtyAddDeviceBinding
import com.njdc.abb.familyguard.ui.base.BaseActivity
import com.njdc.abb.familyguard.util.KeepStateNavigator

class AddDeviceActivity : BaseActivity<AtyAddDeviceBinding>() {

    override fun getLayoutId() = R.layout.aty_add_device

    override fun loadData() {
        with(supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!! as NavHostFragment) {
            navController.navigatorProvider.plusAssign(
                KeepStateNavigator(
                    this@AddDeviceActivity,
                    childFragmentManager,
                    R.id.nav_host_fragment
                )
            )
            navController.setGraph(R.navigation.navi_add_device)
        }
    }
}