package com.njdc.abb.familyguard

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.njdc.abb.familyguard.model.entity.data.Data
import com.njdc.abb.familyguard.model.entity.data.EnvionmentParameter
import com.njdc.abb.familyguard.model.entity.data.EnvironmentType
import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testJson() {
        val json =
            "{\"Method\":\"DownloadEnvironsSpecs\",\"Status\":\"0\",\"Message\":\"\",\"Data\":{\"Dust\":{\"On\":\"NO\",\"Specs\":[\"10000\"]},\"Density\":{\"On\":\"NO\",\"Specs\":[\"75\",\"115\"]},\"Temperature\":{\"On\":\"NO\",\"Specs\":[\"20\",\"30\"]},\"Humidity\":{\"On\":\"NO\",\"Specs\":[\"40\",\"70\"]},\"CO\":{\"On\":\"NO\",\"Specs\":[\"8\"]},\"Smoke\":{\"On\":\"YES\",\"Specs\":[\"0\"]},\"TVOC\":{\"On\":\"NO\",\"Specs\":[\"1000\"]},\"CO2\":{\"On\":\"NO\",\"Specs\":[\"1000\"]},\"Noise\":{\"On\":\"NO\",\"Specs\":[\"85\"]},\"Beam\":{\"On\":\"NO\",\"Specs\":[\"400\"]},\"Motion\":{\"On\":\"NO\",\"Specs\":[\"0\"]}}}"
        val response: BaseResponse<Data> =
            Gson().fromJson(json, object : TypeToken<BaseResponse<Data>>() {}.type)
        val data = response.Data
        val clz = data!!.javaClass
        val list = clz.declaredFields
        val eList = arrayListOf<EnvionmentParameter>()
        list.forEach {
            it.isAccessible = true
            eList.add((it.get(data) as EnvionmentParameter).apply {
                mEnvironmentType = it.name
            })
        }
        val typeList = EnvironmentType.values()
        for (e in eList) {
            for (t in typeList) {
                if (t.mEnvironmentType.contains(e.mEnvironmentType.toString(), true)) {
                    e.mEnvironmentName = t.mEnvironmentName
                    e.mEnvironmentUnity = t.mEnvironmentUnity
                    e.mIconSelected = t.mIconSelected
                }
            }
        }
        eList.size
    }
}
