package com.njdc.abb.familyguard.ui.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.databinding.FrgAdStep2Binding
import com.njdc.abb.familyguard.ui.base.BaseFragment
import com.njdc.abb.familyguard.util.getWifiName
import com.njdc.abb.familyguard.util.set
import com.njdc.abb.familyguard.viewmodel.AddDeviceViewModel

class AdStep2Fragment : BaseFragment<FrgAdStep2Binding>() {

    private val wifiViewModel by viewModels<AddDeviceViewModel>({ requireActivity() }, { factory })

    private val wifiStateReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                if (intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN
                    ) != WifiManager.WIFI_STATE_ENABLED
                ) {
                    wifiViewModel.wifiName.set("")
                } else {
                    wifiViewModel.wifiName.set(activity!!.getWifiName())
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.frg_ad_step2

    override fun loadData() {
        mBinding.vm = wifiViewModel
        wifiViewModel.wifiName.set(activity!!.getWifiName())
        mBinding.btnStep3.setOnClickListener { findNavController().navigate(R.id.action_adStep2Fragment_to_adStep3Fragment) }
        mBinding.tbStep2.setLeftOnClickListener(View.OnClickListener { findNavController().popBackStack() })
        activity!!.registerReceiver(
            wifiStateReceiver,
            IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.unregisterReceiver(wifiStateReceiver)
    }
}

