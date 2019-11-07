package com.njdc.abb.familyguard.ui.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgFindPwdBinding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.get
import com.njdc.abb.familyguard.util.toast
import com.njdc.abb.familyguard.viewmodel.FindPwdViewModel
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class FindPwdFragment : BaseFragment<FrgFindPwdBinding>(), View.OnClickListener {

    private val findPwdViewModel: FindPwdViewModel by viewModels { factory }
    private val userModel: UserViewModel by viewModels({ requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_find_pwd

    override fun loadData() {
        mBinding.vm = findPwdViewModel
        mBinding.clickListener = this
        mBinding.tbFindpwd.setLeftOnClickListener(View.OnClickListener { findNavController().navigateUp() })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register ->
                findPwdViewModel.checkPattern().let {
                    when (it) {
                        "success" -> findPwd()
                        else -> toast(it)
                    }
                }
        }
    }

    private fun findPwd() {
        userModel.findPwd(findPwdViewModel.username.get()!!, findPwdViewModel.phone.get()!!)
            .bindLifeCycle(this).subscribe({
                toast(it.Pwd ?: "find pwd null!")
            }, {
                toast(it.message ?: "find pwd error!")
            })
    }
}