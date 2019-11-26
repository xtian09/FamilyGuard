package com.njdc.abb.familyguard.model.entity.data

open class EnvionmentParameter(
    val On: String,
    val Specs: List<String>,
    var mEnvironmentType: String? = null,
    var mEnvironmentName: Int? = null,
    var mEnvironmentUnity: Int? = null,
    var mIconSelected: Int? = null
)