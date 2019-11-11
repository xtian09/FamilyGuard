package com.njdc.abb.familyguard.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
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
        val a = TintTypedArray.obtainStyledAttributes(
            context, attrs, R.styleable.CustomToolbar, defStyleAttr, 0
        )
        val title = a.getString(R.styleable.CustomToolbar_title)
        if (!TextUtils.isEmpty(title)) {
            setMTitle(title)
        }
        val leftTitle = a.getString(R.styleable.CustomToolbar_left_title)
        if (!TextUtils.isEmpty(leftTitle)) {
            setLeftTitle(leftTitle)
            val backIcon = a.getBoolean(R.styleable.CustomToolbar_backIcon, false)
            setBackIcon(backIcon)
        }
        val leftLogo = a.getDrawable(R.styleable.CustomToolbar_leftLogo)
        if (leftLogo != null) {
            setLeftLogo(leftLogo)
        }
        val rightLogo = a.getDrawable(R.styleable.CustomToolbar_rightLogo)
        if (rightLogo != null) {
            setRightLogo(rightLogo)
        }
        val bootomLine = a.getBoolean(R.styleable.CustomToolbar_bottomLine, false)
        if (bootomLine) {
            line_holder.visibility = View.VISIBLE
        }
        a.recycle()
    }

    private fun setMTitle(title: CharSequence) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.text = title
        }
    }

    private fun setLeftLogo(icon: Drawable) {
        tv_left.visibility = View.GONE
        iv_left.visibility = View.VISIBLE
        iv_left.setImageDrawable(icon)
    }

    private fun setLeftTitle(title: CharSequence) {
        iv_left.visibility = View.GONE
        tv_left.visibility = View.VISIBLE
        tv_left.text = title
    }

    private fun setBackIcon(backIcon: Boolean) {
        if (!backIcon) {
            tv_left.setCompoundDrawables(null, null, null, null)
        }
    }

    private fun setRightLogo(icon: Drawable) {
        iv_right.visibility = View.VISIBLE
        iv_right.setImageDrawable(icon)
    }

    fun setLeftOnClickListener(listener: OnClickListener) {
        if (tv_left.visibility == View.VISIBLE || iv_left.visibility == View.VISIBLE)
            rl_left.setOnClickListener(listener)
    }

    fun setRightOnClickListener(listener: OnClickListener) {
        if (iv_right.visibility == View.VISIBLE)
            rl_right.setOnClickListener(listener)
    }
}