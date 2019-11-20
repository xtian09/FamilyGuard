package com.njdc.abb.familyguard.ui.setting

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep3Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.*
import com.njdc.abb.familyguard.viewmodel.WifiViewModel

class AdStep3Fragment : BaseFragment<FrgAdStep3Binding>() {

    private val wifiViewModel by viewModels<WifiViewModel>({ requireActivity() }, { factory })

    override fun getLayoutId() = R.layout.frg_ad_step3

    override fun loadData() {
        mBinding.tbStep3.setLeftOnClickListener(View.OnClickListener { findNavController().popBackStack() })
        mBinding.ivQrCode.setImageBitmap(wifiViewModel.getQRCode().createQRCode(activity!!.getScreenWidth()))
        wifiViewModel.socketClient().connect().bindLifeCycle(this).subscribe({ dataWrapper ->
            if (dataWrapper.state == Constants.CONNECTING && !dataWrapper.data.isNullOrEmpty()) {
                if (dataWrapper.data.startsWith("EXECUTE_ERROR")) {
                    XPopup.Builder(activity).asCustom(
                        asNormal(
                            context!!,
                            getString(R.string.qr_code_error),
                            getString(R.string.confirm),
                            OnConfirmListener {
                                findNavController().popBackStack()
                            },
                            getString(R.string.cancel),
                            null,
                            false
                        )
                    ).show()
                } else {
                    val type = dataWrapper.data.getValue("Type")
                    if (!type.isNullOrEmpty()) {
                        when {
                            type.startsWith("OffLine") -> {
                                dialog(getString(R.string.abb_offline))
                            }
                            type.startsWith("Link") -> {

                                wifiViewModel.socketClient().disConnect()
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