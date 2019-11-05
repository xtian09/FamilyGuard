package com.njdc.abb.familyguard.ui.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgRegisterBinding
import com.njdc.abb.familyguard.model.entity.data.User
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.get
import com.njdc.abb.familyguard.util.toast
import com.njdc.abb.familyguard.viewmodel.RegisterViewModel
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class RegisterFragment : BaseFragment<FrgRegisterBinding>(), View.OnClickListener {

    private val registerViewModel: RegisterViewModel by viewModels { factory }
    private val userModel: UserViewModel by viewModels({ requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_register

    override fun loadData() {
        mBinding.vm = registerViewModel
        mBinding.clickListener = this
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                registerViewModel.checkPattern().let {
                    when (it) {
                        "success" -> register()
                        else -> toast(it)
                    }
                }
            }
            R.id.tv_login -> findNavController().popBackStack()
        }
    }

    private fun register() {
        val user = User(
            registerViewModel.username.get()!!,
            registerViewModel.password.get()!!,
            registerViewModel.phone.get()!!
        )
        userModel.register(user.Usr, user.Pwd, user.Phone).bindLifeCycle(this).subscribe({
            userModel.setUser(user)
        }, {
            toast(it.message ?: "register frg error!")
        })
    }
}