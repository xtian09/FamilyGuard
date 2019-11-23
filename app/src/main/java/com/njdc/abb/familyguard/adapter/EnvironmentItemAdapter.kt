package com.njdc.abb.familyguard.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.model.entity.data.EnvironmentEntity
import com.njdc.abb.familyguard.model.entity.data.EnvironmentType


class EnvironmentItemAdapter :
    BaseQuickAdapter<EnvironmentEntity, BaseViewHolder>(R.layout.item_environment_type) {

    override fun convert(helper: BaseViewHolder, item: EnvironmentEntity?) {
        helper.setImageResource(R.id.iv_icon, item!!.mIconSelected)
        helper.setText(R.id.tv_name, item.mEnvironmentName)
        helper.setChecked(R.id.cb_check, item.mIsChecked)
    }

    fun selectAll(boolean: Boolean) {
        data.iterator().forEach {
            it.mIsChecked = boolean
        }
        notifyDataSetChanged()
    }

    fun default() {
        data.iterator().forEach {
            it.mIsChecked = it.mEnvironmentType != EnvironmentType.CO2.mEnvironmentType
        }
        notifyDataSetChanged()
    }

}