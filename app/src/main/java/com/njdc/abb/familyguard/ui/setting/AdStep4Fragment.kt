package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.fragment.app.viewModels
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lxj.xpopup.XPopup
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep4Binding
import com.njdc.abb.familyguard.model.entity.data.Rooms
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.ui.widget.AddDevicePopupView
import com.njdc.abb.familyguard.util.*
import com.njdc.abb.familyguard.viewmodel.AddDeviceViewModel
import com.njdc.abb.familyguard.viewmodel.UserViewModel

class AdStep4Fragment : BaseFragment<FrgAdStep4Binding>(), View.OnClickListener {

    private val wifiViewModel by viewModels<AddDeviceViewModel>({ requireActivity() }, { factory })
    private val userViewModel by viewModels<UserViewModel> { factory }

    override fun getLayoutId() = R.layout.frg_ad_step4

    override fun loadData() {
        mBinding.vm = wifiViewModel
        mBinding.clickListener = this
        wifiViewModel.getRoomList().firstOrNull()?.let {
            wifiViewModel.roomName.set(it)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_finish -> {
                wifiViewModel.addDevice(
                    wifiViewModel.roomName.get()!!.RoomID,
                    wifiViewModel.deviceName.get()!!
                ).doOnSubscribe {
                    loading.show()
                }.subscribe({
                    getAllHomeData()
                }, {
                    loading.dismiss()
                    it.message?.let { msg ->
                        toast(msg)
                    }
                })
            }
            R.id.tv_room_name -> {
                if (wifiViewModel.getRoomList().isNullOrEmpty()) {
                    // not gonna happen
                    toast("no room data !!")
                } else {
                    XPopup.Builder(this.activity)
                        .asCustom(AddDevicePopupView(this.activity!!).apply {
                            setData(wifiViewModel.getRoomList())
                            setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                                when (position) {
                                    0 -> return@OnItemClickListener
                                    else -> {
                                        wifiViewModel.roomName.set((adapter.getItem(position) as Rooms))
                                        dismiss()
                                    }
                                }
                            })
                        }).show()
                }
            }
        }
    }

    private fun getAllHomeData() {
        userViewModel.getAllHomeData(wifiViewModel.user).bindLifeCycle(this).subscribe({
            loading.dismiss()
            toast(getString(R.string.add_abb_success))
            this.activity!!.finish()
        }, {
            loading.dismiss()
            dialog(it.message ?: "get homeData error!")
        })
    }
}