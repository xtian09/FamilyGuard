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

class CommonPopupView(context: Context) : CenterPopupView(context), OnClickListener {
    private var cancelListener: OnCancelListener? = null
    private var confirmListener: OnConfirmListener? = null
    private var tvTitle: TextView? = null
    private var tvCancel: TextView? = null
    private var tvConfirm: TextView? = null
    private var lineHolder: View? = null
    private var title: String? = null
    private var cancelText: String? = null
    private var confirmText: String? = null
    private var isHideCancel = false

    override fun getImplLayoutId(): Int {
        return if (bindLayoutId != 0) bindLayoutId else R.layout.dlg_common
    }

    override fun initPopupContent() {
        super.initPopupContent()
        tvTitle = findViewById(R.id.tv_title)
        tvCancel = findViewById(R.id.tv_negative)
        tvConfirm = findViewById(R.id.tv_positive)
        lineHolder = findViewById(R.id.line_holder_second)
        tvCancel!!.setOnClickListener(this)
        tvConfirm!!.setOnClickListener(this)
        if (!TextUtils.isEmpty(title)) {
            tvTitle!!.text = title
        } else {
            tvTitle!!.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(cancelText)) {
            tvCancel!!.text = cancelText
        }
        if (!TextUtils.isEmpty(confirmText)) {
            tvConfirm!!.text = confirmText
        }
        if (isHideCancel) {
            tvCancel!!.visibility = View.GONE
            lineHolder!!.visibility = View.GONE
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

    override fun onClick(v: View) {
        if (v === tvCancel) {
            if (cancelListener != null) cancelListener!!.onCancel()
            dismiss()
        } else if (v === tvConfirm) {
            if (confirmListener != null) confirmListener!!.onConfirm()
            if (popupInfo.autoDismiss) dismiss()
        }
    }
}