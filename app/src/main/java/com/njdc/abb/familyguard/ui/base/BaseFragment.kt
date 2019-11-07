package com.njdc.abb.familyguard.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected lateinit var mBinding: VB

    val loading: LoadingPopupView by lazy {
        checkNotNull(activity) { throw IllegalStateException("activity is null") }
        if (activity is BaseActivity<*>) {
            val baseActivity = activity as BaseActivity<*>
            return@lazy baseActivity.loading
        } else {
            throw IllegalStateException("activity is not BaseActivity")
        }
    }

    val factory: ViewModelProvider.Factory by lazy {
        checkNotNull(activity) { throw IllegalStateException("activity is null") }
        if (activity is BaseActivity<*>) {
            val baseActivity = activity as BaseActivity<*>
            return@lazy baseActivity.factory
        } else {
            throw IllegalStateException("activity is not BaseActivity")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    abstract fun getLayoutId(): Int

    abstract fun loadData()

    protected fun <T> autoWired(key: String, default: T? = null): T? =
        arguments?.let { findWired(it, key, default) }

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