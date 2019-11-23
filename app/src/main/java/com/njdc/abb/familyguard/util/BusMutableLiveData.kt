package com.njdc.abb.familyguard.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class BusMutableLiveData<T> : MutableLiveData<T>() {

    override fun observe(
        owner: LifecycleOwner,
        observer: Observer<in T>
    ) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        try {
            val mObservers = liveDataClass.getDeclaredField("mObservers")
            mObservers.isAccessible = true
            val observers = mObservers[this]
            val observersClass: Class<*> = observers.javaClass
            val methodGet =
                observersClass.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            val objectWrapperEntry = methodGet.invoke(observers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("ObserverWrapper can not be null")
            }
            val wrapperClass: Class<*>? = objectWrapper.javaClass.superclass
            val mLastVersion =
                wrapperClass!!.getDeclaredField("mLastVersion")
            mLastVersion.isAccessible = true
            val mVersion = liveDataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true
            val mV = mVersion[this]
            mLastVersion[objectWrapper] = mV
            mObservers.isAccessible = false
            methodGet.isAccessible = false
            mLastVersion.isAccessible = false
            mVersion.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}