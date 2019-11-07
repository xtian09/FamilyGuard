package com.njdc.abb.familyguard.ui.environment

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgEnvironmentBinding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.login
import com.njdc.abb.familyguard.util.toFlowable
import com.njdc.abb.familyguard.util.toast
import com.njdc.abb.familyguard.viewmodel.UserViewModel


class EnvironmentFragment : BaseFragment<FrgEnvironmentBinding>(), View.OnClickListener {

    private val userModel: UserViewModel by viewModels({ requireActivity() }, { factory })

    override fun getLayoutId(): Int {
        return R.layout.frg_environment
    }

    override fun loadData() {
        mBinding.tbEnvironment.setLeftOnClickListener(View.OnClickListener {
            userModel.logout()
            login()
        })
        mBinding.tbEnvironment.inflateMenu(R.menu.menu_environment)
        mBinding.tbEnvironment.overflowIcon = resources.getDrawable(R.mipmap.ic_left_back, null)

        userModel.home.toFlowable().bindLifeCycle(this).subscribe({
            mBinding.tvHome.text = it.HomeName
        }, {

        })
        userModel.home.toFlowable().bindLifeCycle(this).subscribe({
            with(mBinding.tlEnvironment) {
                removeAllTabs()
                for (i in it.Rooms) {
                    val tab = newTab().setIcon(R.mipmap.ic_launcher)
                    tab.text = i.RoomName
                    mBinding.tlEnvironment.addTab(tab)
                }
                val tab = newTab().setIcon(R.mipmap.ic_launcher)
                tab.text = getString(R.string.add)
                mBinding.tlEnvironment.addTab(tab)
            }
        }, {

        })
        mBinding.tlEnvironment.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0!!.text == getString(R.string.add)) {
                    toast("new")
                } else {
                    toast("normal")
                }
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}