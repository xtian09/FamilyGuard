package com.njdc.abb.familyguard.ui.login

import android.view.View
import androidx.fragment.app.viewModels
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgRegisterBinding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class RegisterFragment : BaseFragment<FrgRegisterBinding>(), View.OnClickListener {

    private val userModel: UserViewModel by viewModels({ this.requireActivity() }, { factory })

    override fun getLayoutId(): Int {
        return R.layout.frg_register
    }

    override fun loadData() {

    }

    override fun onClick(v: View?) {

    }
}