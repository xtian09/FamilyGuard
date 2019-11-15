package com.njdc.abb.familyguard.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.TintTypedArray
import androidx.appcompat.widget.Toolbar
import com.njdc.abb.familyguard.R
import kotlinx.android.synthetic.main.widget_toolbar.view.*

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.widget_toolbar, this)
        with(
            TintTypedArray.obtainStyledAttributes(
                context, attrs, R.styleable.CustomToolbar, defStyleAttr, 0
            )
        ) {
            val title = getString(R.styleable.CustomToolbar_title)
            if (!TextUtils.isEmpty(title)) {
                tv_title.text = title
            }
            val leftTitle = getString(R.styleable.CustomToolbar_leftTitle)
            if (!TextUtils.isEmpty(leftTitle)) {
                tv_left.visibility = View.VISIBLE
                tv_left.text = leftTitle
                if (!getBoolean(R.styleable.CustomToolbar_leftTitleIcon, false)) {
                    tv_left.setCompoundDrawables(null, null, null, null)
                }
            } else {
                getDrawable(R.styleable.CustomToolbar_leftLogo)?.let {
                    iv_left.visibility = View.VISIBLE
                    iv_left.setImageDrawable(it)
                }
            }
            val rightTitle = getString(R.styleable.CustomToolbar_rightTitle)
            if (!TextUtils.isEmpty(rightTitle)) {
                tv_right.visibility = View.VISIBLE
                tv_right.text = rightTitle
            } else {
                getDrawable(R.styleable.CustomToolbar_rightLogo)?.let {
                    iv_right.visibility = View.VISIBLE
                    iv_right.setImageDrawable(it)
                }
            }
            if (getBoolean(R.styleable.CustomToolbar_bottomLine, false)) {
                line_holder.visibility = View.VISIBLE
            }
            recycle()
        }
    }

    fun setLeftOnClickListener(listener: OnClickListener) {
        if (tv_left.visibility == View.VISIBLE)
            tv_left.setOnClickListener(listener)
        if (iv_left.visibility == View.VISIBLE)
            iv_left.setOnClickListener(listener)
    }

    fun setRightOnClickListener(listener: OnClickListener) {
        if (iv_right.visibility == View.VISIBLE)
            iv_right.setOnClickListener(listener)
    }
}