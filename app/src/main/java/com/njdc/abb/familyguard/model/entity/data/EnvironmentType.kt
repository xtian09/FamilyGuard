package com.njdc.abb.familyguard.model.entity.data

import com.njdc.abb.familyguard.R

enum class EnvironmentType(
    private val mEnvironmentType: String,
    private val mEnvironmentName: Int,
    private val mEnvironmentUnity: Int,
    private val mIconSelected: Int,
    private val mIconNormal: Int
) {
    Destiny(
        "DENSITY",
        R.string.environment_dust_name,
        R.string.environment_dust_unit,
        R.mipmap.ic_dust_enable,
        R.mipmap.ic_dust_unable
    ),
    Dust(
        "DUST",
        R.string.environment_dust_name,
        R.string.environment_dust_num_unit,
        R.mipmap.ic_dust_enable,
        R.mipmap.ic_dust_unable
    ),
    Temperature(
        "TEMPERATURE",
        R.string.environment_temperature_name,
        R.string.environment_temperature_unit,
        R.mipmap.ic_temperatrue_enable,
        R.mipmap.ic_temperature_unable
    ),
    Humidity(
        "HUMIDITY",
        R.string.environment_humidity_name,
        R.string.environment_humidity_unit,
        R.mipmap.ic_humidity_enable,
        R.mipmap.ic_humidity_unable
    ),
    CO(
        "CO",
        R.string.environment_co_name,
        R.string.environment_co_unit,
        R.mipmap.ic_co_enable,
        R.mipmap.ic_co_unable
    ),
    Smoke(
        "SMOKE",
        R.string.environment_smoke_name,
        R.string.environment_null_unit,
        R.mipmap.ic_smoke_enable,
        R.mipmap.ic_smoke_unable
    ),
    TVOC(
        "TVOC",
        R.string.environment_tvoc_name,
        R.string.environment_tvoc_unit,
        R.mipmap.ic_tvoc_enable,
        R.mipmap.ic_tvoc_unable
    ),
    CO2(
        "CO2",
        R.string.environment_co2_name,
        R.string.environment_co2_unit,
        R.mipmap.ic_co2_enable,
        R.mipmap.ic_co2_unable
    ),
    Noise(
        "NOISE",
        R.string.environment_noise_name,
        R.string.environment_noise_unit,
        R.mipmap.ic_noise_enable,
        R.mipmap.ic_noise_unable
    ),
    Light(
        "BEAM1",
        R.string.environment_light_name,
        R.string.environment_light_unit,
        R.mipmap.ic_light_enable,
        R.mipmap.ic_light_unable
    ),
    Motion(
        "MOTION1",
        R.string.environment_motion_name,
        R.string.environment_null_unit,
        R.mipmap.ic_motion_enable,
        R.mipmap.ic_motion_unable
    ),
    Methanal(
        "",
        R.string.environment_methanal_name,
        R.string.environment_null_unit,
        R.mipmap.ic_methanal_enable,
        R.mipmap.ic_methanal_unable
    );
}