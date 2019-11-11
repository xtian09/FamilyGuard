package com.njdc.abb.familyguard.ui.home

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.AtyHomeBinding
import com.njdc.abb.familyguard.ui.base.BaseActivity

class HomeActivity : BaseActivity<AtyHomeBinding>() {

    override fun getLayoutId() = R.layout.aty_home

    override fun loadData() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_home) as NavHostFragment
        NavigationUI.setupWithNavController(mBinding.bnvHome, navHostFragment.navController)
        mBinding.bnvHome.itemIconTintList = null
    }
}
