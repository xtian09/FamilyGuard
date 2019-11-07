package com.njdc.abb.familyguard.ui.login

import androidx.activity.viewModels
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.AtyLoginBinding
import com.njdc.abb.familyguard.model.entity.UserSource
import com.njdc.abb.familyguard.model.entity.data.User
import com.njdc.abb.familyguard.ui.base.BaseActivity
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.launchMain
import com.njdc.abb.familyguard.util.toFlowable
import com.njdc.abb.familyguard.util.toast
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class LoginActivity : BaseActivity<AtyLoginBinding>() {

    private val userModel: UserViewModel by viewModels { factory }

    companion object {
        const val KEY_LOGIN: String = "KEY_LOGIN"
    }

    override fun getLayoutId() = R.layout.aty_login

    override fun loadData() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navGraph: NavGraph =
            navHostFragment.navController.navInflater.inflate(R.navigation.navi_login)
        navGraph.startDestination = if (autoWired(KEY_LOGIN, false) == true) {
            R.id.loginFrg
        } else {
            R.id.welcomeFrg
        }
        navHostFragment.navController.graph = navGraph

        userModel.user.toFlowable().bindLifeCycle(this).subscribe({
            if (it.status == UserSource.Status.AUTHENTICATED) {
                getAllHomeData(it.data!!)
            }
        }, {
            toast(it.message ?: "login aty error!")
        })
    }

    private fun getAllHomeData(user: User) {
        userModel.getAllHomeData(user.Usr).doOnSubscribe {

        }.bindLifeCycle(this).subscribe({
            launchMain()
        }, {
            toast(it.message ?: "get homeData error!")
            userModel.error(it.message ?: "get homeData error!")
        })


    }
}
