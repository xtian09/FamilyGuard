package com.njdc.abb.familyguard.ui.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgLoginBinding
import com.njdc.abb.familyguard.model.entity.data.User
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.dialog
import com.njdc.abb.familyguard.util.get
import com.njdc.abb.familyguard.viewmodel.LoginViewModel
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class LoginFragment : BaseFragment<FrgLoginBinding>(), View.OnClickListener {

    private val loginModel: LoginViewModel by viewModels { factory }
    private val userModel: UserViewModel by viewModels({ this.requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_login

    override fun loadData() {
        mBinding.vm = loginModel
        mBinding.clickListener = this
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val user = User(
                    loginModel.username.get()!!,
                    loginModel.password.get()!!
                )
                userModel.login(
                    user.Usr,
                    user.Pwd
                ).doOnSubscribe {
                    loading.show()
                }.bindLifeCycle(this).subscribe({
                    loading.dismiss()
                    userModel.setUser(user)
                }, {
                    loading.dismiss()
                    dialog(it.message ?: "login frg error!")
                })
            }
            R.id.tv_forget ->
                findNavController().navigate(R.id.action_loginFrg_to_findPwdFrg)
            R.id.tv_register ->
                findNavController().navigate(R.id.action_loginFrg_to_registerFrg)
        }
    }
}