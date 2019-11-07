package com.njdc.abb.familyguard.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.text.ParcelableSpan
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import com.njdc.abb.familyguard.ui.home.HomeActivity
import com.njdc.abb.familyguard.ui.login.LoginActivity
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
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

fun Activity.launchMain() =
    this.apply {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

fun Fragment.launchMain() =
    this.apply {
        startActivity(Intent(activity, HomeActivity::class.java))
        activity!!.finish()
    }

fun Fragment.login() =
    this.apply {
        startActivity(
            Intent(activity, LoginActivity::class.java).apply {
                putExtra(
                    LoginActivity.KEY_LOGIN,
                    true
                )
            })
        activity!!.finish()
    }

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply {
    postValue(t)
}

fun <T> Observable<T>.sync(): Observable<T> =
    this.subscribeOn(AndroidSchedulers.mainThread()).observeOn(
        AndroidSchedulers.mainThread()
    )

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

fun <T> Observable<T>.bindLifeCycle(owner: LifecycleOwner): ObservableSubscribeProxy<T> =
    this.`as`(
        AutoDispose.autoDisposable(
            AndroidLifecycleScopeProvider.from(
                owner,
                Lifecycle.Event.ON_DESTROY
            )
        )
    )

fun <T> Single<BaseResponse<T>>.handleResult(): Single<BaseResponse<T>> {
    return this.compose { upstream ->
        upstream.flatMap { t: BaseResponse<T> ->
            if (t.Status == "0") {
                return@flatMap Single.just(t)
            } else {
                return@flatMap Single.error<BaseResponse<T>>(Throwable(t.Message))
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

fun CharSequence.formatStringColor(
    context: Context,
    color: Int,
    start: Int,
    end: Int
): SpannableString {
    return this.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end)
}

@BindingAdapter("app:secondColor")
fun secondColor(
    view: TextView,
    color: Int
) {
    view.text?.let {
        view.text = it.formatStringColor(
            view.context,
            color,
            it.length - 2,
            it.length
        )
    }
}

private fun CharSequence.setSpan(span: ParcelableSpan, start: Int, end: Int): SpannableString {
    val spannableString = SpannableString(this)
    spannableString.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return spannableString
}


fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.getScreenWidth(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}

fun Context.getScreenHeight(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}

//fun TabLayout.setTab(titles: Array<String>) {
//    titles.forEach {
//        val view = inflate(R.layout.layout_tab_title)
//        val tvTitle = view.findViewById<TextView>(R.id.tv_tab_name)
//        tvTitle.text = it
//        addTab(newTab().setCustomView(view))
//    }
//}

var View.topMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).topMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
    }