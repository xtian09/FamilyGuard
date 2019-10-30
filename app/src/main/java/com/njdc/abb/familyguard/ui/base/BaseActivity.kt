package com.njdc.abb.familyguard.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.njdc.abb.familyguard.FGApp


abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    val factory: ViewModelProvider.Factory by lazy {
        if (application is FGApp) {
            return@lazy (application as FGApp).factory
        } else {
            throw IllegalStateException("application is not PaoApp")
        }
//        throw IllegalStateException("application is not PaoApp")
    }

    protected inline fun <reified T : ViewModel> getInjectViewModel() =
        ViewModelProviders.of(this, factory).get(T::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<VB>(this, getLayoutId())
        mBinding.lifecycleOwner = this
        mContext = this
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