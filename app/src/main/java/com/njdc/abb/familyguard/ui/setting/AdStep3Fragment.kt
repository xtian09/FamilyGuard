package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep3Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.*
import com.njdc.abb.familyguard.viewmodel.AddDeviceViewModel

class AdStep3Fragment : BaseFragment<FrgAdStep3Binding>() {

    private val wifiViewModel by viewModels<AddDeviceViewModel>({ requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_ad_step3

    override fun loadData() {
        wifiViewModel.wifiName.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                wifiViewModel.socketClient().disConnect()
                dialog(getString(R.string.net_error), OnConfirmListener {
                    findNavController().popBackStack()
                })
            }
        })
        mBinding.tbStep3.setLeftOnClickListener(View.OnClickListener { findNavController().popBackStack() })
        mBinding.ivQrCode.setImageBitmap(wifiViewModel.getQRCode().createQRCode(activity!!.getScreenWidth()))
        wifiViewModel.socketClient().connect().async().bindLifeCycle(this)
            .subscribe({ dataWrapper ->
            if (dataWrapper.state == Constants.CONNECTING && !dataWrapper.data.isNullOrEmpty()) {
                if (dataWrapper.data.startsWith("EXECUTE_ERROR")) {
                    wifiViewModel.socketClient().disConnect()
                    dialog(getString(R.string.qr_code_error), OnConfirmListener {
                        findNavController().popBackStack()
                    })
                } else {
                    val type = dataWrapper.data.getValue("Type")
                    if (!type.isNullOrEmpty()) {
                        when {
                            type.startsWith("OffLine") -> {
                                dialog(getString(R.string.abb_offline))
                            }
                            type.startsWith("Link") -> {
                                wifiViewModel.socketClient().disConnect()
                                wifiViewModel.deviceId = dataWrapper.data.getValue("DeviceID")!!
                                wifiViewModel.productId = dataWrapper.data.getValue("Productid")!!
                                findNavController().navigate(R.id.action_adStep3Fragment_to_adStep4Fragment)
                            }
                            type.startsWith("Reveive") -> {
                                toast("receive qr code !")
                            }
                        }
                    }
                }
            }
        }, {
            it.message?.let { string ->
                toast(string)
            }
        })
    }
}