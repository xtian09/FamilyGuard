package com.njdc.abb.familyguard.ui.environment

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgEnvironmentBinding
import com.njdc.abb.familyguard.model.entity.data.Homes
import com.njdc.abb.familyguard.model.entity.data.RoomType
import com.njdc.abb.familyguard.model.entity.data.Rooms
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.ui.setting.AddDeviceActivity
import com.njdc.abb.familyguard.util.*
import com.njdc.abb.familyguard.viewmodel.UserViewModel
import io.reactivex.functions.BiFunction

class EnvironmentFragment : BaseFragment<FrgEnvironmentBinding>(), View.OnClickListener {

    private val userModel: UserViewModel by viewModels({ requireActivity() }, { factory })
    private var nowRoomIndex = 0

    override fun getLayoutId(): Int {
        return R.layout.frg_environment
    }

    override fun loadData() {
        mBinding.tbEnvironment.setLeftOnClickListener(View.OnClickListener {
            XPopup.Builder(activity).asCustom(
                asNormal(
                    context!!, getString(R.string.logout_tip), getString(R.string.confirm),
                    OnConfirmListener {
                        userModel.logout()
                        launchLogin()
                    },
                    getString(R.string.cancel), null, false
                )
            ).show()
        })
        mBinding.tbEnvironment.setRightOnClickListener(View.OnClickListener {
            XPopup.Builder(this.activity).hasShadowBg(false).atView(it).asAttachList(
                arrayOf(
                    getString(R.string.add_device),
                    getString(R.string.add_facility),
                    getString(R.string.btn_control)
                ), null
            ) { position, _ ->
                when (position) {
                    0 -> startActivity(
                        Intent(
                            context,
                            AddDeviceActivity::class.java
                        )
                    )
                    1 -> {
                    }
                    2 -> {
                    }
                }
            }.show()
        })
        mBinding.tlEnvironment.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it.text != getString(RoomType.Add.mRoomName)) {
                        it.setIcon((it.tag as Rooms).mIconNormal!!)
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it.text == getString(RoomType.Add.mRoomName)) {
                        toast("new")
                    } else {
                        it.setIcon((it.tag as Rooms).mIconSelected!!)
                    }
                }
            }

        })
        userModel.home.toFlowable().zipWith(userModel.room.toFlowable(),
            BiFunction<Homes, Rooms, Homes> { t1, _ ->
                for (i in t1.Rooms) {
                    for (type in RoomType.values()) {
                        if (i.RoomType == type.mRoomType) {
                            i.mRoomName = type.mRoomName
                            i.mIconNormal = type.mIconNormal
                            i.mIconSelected = type.mIconSelected
                        }
                    }
                }
                return@BiFunction t1
            }).bindLifeCycle(this).subscribe({
            mBinding.tvHome.text = it.HomeName
            with(mBinding.tlEnvironment) {
                removeAllTabs()
                for (i in it.Rooms) {
                    mBinding.tlEnvironment.addTab(newTab().apply {
                        setIcon(i.mIconNormal!!)
                        text = getString(i.mRoomName!!)
                        tag = i
                    })
                    if (userModel.room.get()!!.RoomID == i.RoomID) {
                        nowRoomIndex = it.Rooms.indexOf(i)
                    }
                }
                val tab = newTab().setIcon(RoomType.Add.mIconNormal)
                tab.text = getString(RoomType.Add.mRoomName)
                mBinding.tlEnvironment.addTab(newTab().apply {
                    setIcon(RoomType.Add.mIconNormal)
                    text = getString(RoomType.Add.mRoomName)
                })
                mBinding.tlEnvironment.postDelayed({
                    mBinding.tlEnvironment.getTabAt(nowRoomIndex)!!.select()
                }, 100)
            }
        }, {
            toast("error")
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}