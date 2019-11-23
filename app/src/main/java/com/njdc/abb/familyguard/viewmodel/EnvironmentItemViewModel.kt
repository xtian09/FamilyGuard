package com.njdc.abb.familyguard.viewmodel

import androidx.lifecycle.ViewModel
import com.njdc.abb.familyguard.model.entity.data.EnvironmentEntity
import com.njdc.abb.familyguard.util.SpManager
import com.njdc.abb.familyguard.util.get
import javax.inject.Inject

class EnvironmentItemViewModel @Inject constructor() : ViewModel() {

    val enviEntities = SpManager.enviEntities

    val envis = arrayListOf<EnvironmentEntity>()

    fun setEnviEntities(eList: MutableList<EnvironmentEntity>) {
        SpManager.setEnviEntities(eList)
    }

    init {
        enviEntities.get()!!.forEach {
            envis.add(it.copy())
        }
    }

}