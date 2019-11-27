package com.njdc.abb.familyguard.ui.widget

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.core.BottomPopupView
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.model.entity.data.Rooms
import kotlinx.android.synthetic.main.dlg_add_device.view.*

class AddDevicePopupView(context: Context) : BottomPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.dlg_add_device
    }

    override fun initPopupContent() {
        super.initPopupContent()
        val adapter = RoomAdapter()
        rv_homes.adapter = adapter
        rv_homes.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        data?.let {
            adapter.setNewData(it)
        }
        itemClickListener?.let {
            adapter.onItemClickListener = it
        }
        btn_cancel.setOnClickListener {
            dismiss()
        }
    }

    inner class RoomAdapter : BaseQuickAdapter<Rooms, BaseViewHolder>(R.layout.item_room) {
        override fun convert(helper: BaseViewHolder, item: Rooms?) {
            when (helper.layoutPosition) {
                0 -> {
                    helper.setTextColor(R.id.tv_name, resources.getColor(R.color.gray, null))
                    helper.setText(R.id.tv_name, R.string.pls_choose_room)
                }
                else -> helper.setText(R.id.tv_name, item!!.RoomName)
            }
        }
    }

    private var itemClickListener: BaseQuickAdapter.OnItemClickListener? = null

    fun setOnItemClickListener(itemClickListener: BaseQuickAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private var data: List<Rooms>? = null

    fun setData(data: List<Rooms>) {
        this.data = data
    }

}