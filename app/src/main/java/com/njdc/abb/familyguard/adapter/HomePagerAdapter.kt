package com.njdc.abb.familyguard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.njdc.abb.familyguard.ui.environment.EnvironmentFragment
import com.njdc.abb.familyguard.ui.facility.FacilityFragment
import com.njdc.abb.familyguard.ui.message.MessageFragment
import com.njdc.abb.familyguard.ui.setting.SettingFragment
import com.njdc.abb.familyguard.ui.surveillance.SurveillanceFragment

class HomePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    companion object {
        const val ENVIRONMENT_INDEX = 0
        const val SURVEILLANCE_INDEX = 1
        const val FACILITY_INDEX = 2
        const val MESSAGE_INDEX = 3
        const val SETTING_INDEX = 4
    }

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        ENVIRONMENT_INDEX to { EnvironmentFragment() },
        SURVEILLANCE_INDEX to { SurveillanceFragment() },
        FACILITY_INDEX to { FacilityFragment() },
        MESSAGE_INDEX to { MessageFragment() },
        SETTING_INDEX to { SettingFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}