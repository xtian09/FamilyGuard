package com.njdc.abb.familyguard.ui.environment

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgEnvironmentBinding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.bindLifeCycle
import com.njdc.abb.familyguard.util.launchLogin
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
            launchLogin()
        })
        mBinding.tbEnvironment.setRightOnClickListener(View.OnClickListener {
            XPopup.Builder(this.activity).hasShadowBg(false).atView(it).asAttachList(
                arrayOf("添加设备", "添加家电", "开关控制"), null
            ) { position, _ ->
                when (position) {
                    0 -> ""
                    1 -> ""
                    2 -> ""
                }
            }.show()
        })
        userModel.home.toFlowable().bindLifeCycle(this).subscribe({
            mBinding.tvHome.text = it.HomeName
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
            toast("error")
        })
        mBinding.tlEnvironment.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text == getString(R.string.add)) {
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