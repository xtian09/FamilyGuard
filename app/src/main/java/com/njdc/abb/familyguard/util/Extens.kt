package com.njdc.abb.familyguard.util

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.njdc.abb.familyguard.model.entity.BaseResponse
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun Activity.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Fragment.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.activity, msg, duration).show()
}

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply {
    postValue(t)
}

fun <T> Flowable<T>.async(withDelay: Long = 0): Flowable<T> =
    this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(
        AndroidSchedulers.mainThread()
    )

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
    this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(
        AndroidSchedulers.mainThread()
    )

fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
    this.`as`(
        AutoDispose.autoDisposable(
            AndroidLifecycleScopeProvider.from(
                owner,
                Lifecycle.Event.ON_DESTROY
            )
        )
    )

fun <T> Flowable<T>.bindLifeCycle(owner: LifecycleOwner): FlowableSubscribeProxy<T> =
    this.`as`(
        AutoDispose.autoDisposable(
            AndroidLifecycleScopeProvider.from(
                owner,
                Lifecycle.Event.ON_DESTROY
            )
        )
    )

fun <R : BaseResponse> Single<R>.getOriginData(): Single<R> {
    return this.compose { upstream ->
        upstream.flatMap { t: R ->
            with(t) {
                if (Status == "0") {
                    return@flatMap Single.just(t)
                } else {
                    return@flatMap Single.error<R>(Throwable(Message))
                }
            }
        }
    }
}

fun <T> LiveData<T>.toFlowable(): Flowable<T> = Flowable.create({ emitter ->
    val observer = Observer<T> { data ->
        data?.let { emitter.onNext(it) }
    }
    observeForever(observer)
    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeObserver(observer)
        }
    }
}, BackpressureStrategy.LATEST)