package com.njdc.abb.familyguard.ui.widget

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.njdc.abb.familyguard.R
import kotlinx.android.synthetic.main.dlg_common.view.*

class CommonPopupView(context: Context) : CenterPopupView(context) {
    private var cancelListener: OnCancelListener? = null
    private var confirmListener: OnConfirmListener? = null
    private var title: String? = null
    private var cancelText: String? = null
    private var confirmText: String? = null
    private var isHideCancel = false

    override fun getImplLayoutId() = R.layout.dlg_common

    override fun initPopupContent() {
        super.initPopupContent()
        tv_negative.setOnClickListener {
            if (cancelListener != null) cancelListener!!.onCancel()
        }
        tv_positive.setOnClickListener {
            if (confirmListener != null) confirmListener!!.onConfirm()
            if (popupInfo.autoDismiss) dismiss()
        }
        if (!TextUtils.isEmpty(title)) {
            tv_title.text = title
        } else {
            tv_title.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(cancelText)) {
            tv_negative.text = cancelText
        }
        if (!TextUtils.isEmpty(confirmText)) {
            tv_positive.text = confirmText
        }
        if (isHideCancel) {
            tv_negative.visibility = View.GONE
            line_holder_second.visibility = View.GONE
        }
    }

    fun setListener(
        confirmListener: OnConfirmListener?,
        cancelListener: OnCancelListener?
    ): CommonPopupView {
        this.cancelListener = cancelListener
        this.confirmListener = confirmListener
        return this
    }

    fun setTitleContent(title: String?): CommonPopupView {
        this.title = title
        return this
    }

    fun setCancelText(cancelText: String?): CommonPopupView {
        this.cancelText = cancelText
        return this
    }

    fun setConfirmText(confirmText: String?): CommonPopupView {
        this.confirmText = confirmText
        return this
    }

    fun hideCancelBtn(): CommonPopupView {
        isHideCancel = true
        return this
    }
}