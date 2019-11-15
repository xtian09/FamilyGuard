package com.njdc.abb.familyguard.ui.login

import android.Manifest
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgWelcomeBinding
import com.njdc.abb.familyguard.model.entity.UserSource
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.toFlowable
import com.njdc.abb.familyguard.viewmodel.UserViewModel
import com.tbruyelle.rxpermissions2.RxPermissions


class WelcomeFragment : BaseFragment<FrgWelcomeBinding>() {

    private val userModel: UserViewModel by viewModels({ this.requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_welcome

    override fun loadData() {
        RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION).bindLifeCycle(this)
            .subscribe { hasPermission ->
                if (hasPermission) {
                    userModel.user.toFlowable().bindLifeCycle(this).subscribe {
                        if (it.status != UserSource.Status.AUTHENTICATED) {
                            findNavController().navigate(R.id.action_welcomeFrg_to_loginFrg)
                        }
                    }
                } else {
                    activity!!.finish()
                }
            }
    }
}