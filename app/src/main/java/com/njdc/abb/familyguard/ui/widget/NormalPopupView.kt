package com.njdc.abb.familyguard.ui.widget

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.njdc.abb.familyguard.R

class NormalPopupView(context: Context) : CenterPopupView(context), OnClickListener {
    private var cancelListener: OnCancelListener? = null
    private var confirmListener: OnConfirmListener? = null
    private var tv_title: TextView? = null
    private var tv_cancel: TextView? = null
    private var tv_confirm: TextView? = null
    private var line_holder: View? = null
    private var title: String? = null
    private var cancelText: String? = null
    private var confirmText: String? = null
    private var isHideCancel = false

    override fun getImplLayoutId(): Int {
        return if (bindLayoutId != 0) bindLayoutId else R.layout.dlg_normal
    }

    override fun initPopupContent() {
        super.initPopupContent()
        tv_title = findViewById(R.id.tv_title)
        tv_cancel = findViewById(R.id.tv_negative)
        tv_confirm = findViewById(R.id.tv_positive)
        line_holder = findViewById(R.id.line_holder)

        tv_cancel!!.setOnClickListener(this)
        tv_confirm!!.setOnClickListener(this)
        if (!TextUtils.isEmpty(title)) {
            tv_title!!.text = title
        } else {
            tv_title!!.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(cancelText)) {
            tv_cancel!!.text = cancelText
        }
        if (!TextUtils.isEmpty(confirmText)) {
            tv_confirm!!.text = confirmText
        }
        if (isHideCancel) {
            tv_cancel!!.visibility = View.GONE
            line_holder!!.visibility = View.GONE
        }
    }

    fun setListener(
        confirmListener: OnConfirmListener?,
        cancelListener: OnCancelListener?
    ): NormalPopupView {
        this.cancelListener = cancelListener
        this.confirmListener = confirmListener
        return this
    }

    fun setTitleContent(title: String?): NormalPopupView {
        this.title = title
        return this
    }

    fun setCancelText(cancelText: String?): NormalPopupView {
        this.cancelText = cancelText
        return this
    }

    fun setConfirmText(confirmText: String?): NormalPopupView {
        this.confirmText = confirmText
        return this
    }

    fun hideCancelBtn(): NormalPopupView {
        isHideCancel = true
        return this
    }

    override fun onClick(v: View) {
        if (v === tv_cancel) {
            if (cancelListener != null) cancelListener!!.onCancel()
            dismiss()
        } else if (v === tv_confirm) {
            if (confirmListener != null) confirmListener!!.onConfirm()
            if (popupInfo.autoDismiss) dismiss()
        }
    }
}