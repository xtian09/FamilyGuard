package com.njdc.abb.familyguard.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.njdc.abb.familyguard.FGApp

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mBinding: VB

    val loading: LoadingPopupView by lazy {
        XPopup.Builder(this).dismissOnTouchOutside(false).asLoading()
    }

    val factory: ViewModelProvider.Factory by lazy {
        if (application is FGApp) {
            return@lazy (application as FGApp).factory
        } else {
            throw IllegalStateException("application is not PaoApp")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
        loadData()
    }

    abstract fun getLayoutId(): Int

    abstract fun loadData()

    protected fun <T> autoWired(key: String, default: T? = null): T? {
        return intent?.extras?.let { findWired(it, key, default) }
    }

    private fun <T> findWired(bundle: Bundle, key: String, default: T? = null): T? {
        return if (bundle.get(key) != null) {
            try {
                bundle.get(key) as T
            } catch (e: ClassCastException) {
                e.printStackTrace()
                null
            }
        } else default

    }
}