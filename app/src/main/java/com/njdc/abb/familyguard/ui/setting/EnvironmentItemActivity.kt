package com.njdc.abb.familyguard.ui.setting

import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.njdc.abb.familyguard.R
import com.njdc.abb.familyguard.adapter.EnvironmentItemAdapter
import com.njdc.abb.familyguard.databinding.AtyEnvironmentItemBinding
import com.njdc.abb.familyguard.ui.base.BaseActivity
import com.njdc.abb.familyguard.ui.widget.EnviDecoration
import com.njdc.abb.familyguard.util.dialog
import com.njdc.abb.familyguard.util.toast
import com.njdc.abb.familyguard.viewmodel.EnvironmentItemViewModel

class EnvironmentItemActivity : BaseActivity<AtyEnvironmentItemBinding>(), View.OnClickListener {

    private val environmentItemViewModel by viewModels<EnvironmentItemViewModel> { factory }

    private lateinit var mAdapter: EnvironmentItemAdapter

    override fun getLayoutId(): Int = R.layout.aty_environment_item

    override fun loadData() {
        mBinding.clickListener = this
        mAdapter = EnvironmentItemAdapter()
        mBinding.rvEnvironment.adapter = mAdapter
        mAdapter.setNewData(environmentItemViewModel.envis)
        mBinding.rvEnvironment.addItemDecoration(EnviDecoration(this))
        mAdapter.setOnItemClickListener { _, view, position ->
            mAdapter.data[position].mIsChecked =
                !view.findViewById<CheckBox>(R.id.cb_check).isChecked
            mAdapter.notifyItemChanged(position)
            isAllSelected()
        }
        mBinding.cbSelectAll.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!buttonView.isPressed)
                return@setOnCheckedChangeListener
            mAdapter.selectAll(isChecked)
        }
        mBinding.tbEnvironmentItem.setLeftOnClickListener(View.OnClickListener {
            finish()
        })
        mBinding.tbEnvironmentItem.setRightOnClickListener(View.OnClickListener {
            if (!selectCheck()) {
                dialog(getString(R.string.environment_need_one))
            } else {
                environmentItemViewModel.setEnviEntities(mAdapter.data)
            }
        })
        environmentItemViewModel.enviEntities.observe(this, Observer {
            toast(getString(R.string.save_success))
            finish()
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_default -> mAdapter.default()
        }
    }

    private fun isAllSelected(): Boolean {
        for (e in mAdapter.data) {
            if (!e.mIsChecked) {
                mBinding.cbSelectAll.isChecked = false
                return false
            }
        }
        mBinding.cbSelectAll.isChecked = true
        return true
    }

    private fun selectCheck(): Boolean {
        for (e in mAdapter.data) {
            if (e.mIsChecked) {
                return true
            }
        }
        return false
    }
}