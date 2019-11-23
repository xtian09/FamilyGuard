package com.njdc.abb.familyguard.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.njdc.abb.familyguard.model.entity.http.BaseResponse
import com.njdc.abb.familyguard.ui.home.HomeActivity
import com.njdc.abb.familyguard.ui.login.LoginActivity
import com.njdc.abb.familyguard.ui.setting.AddDeviceActivity
import com.njdc.abb.familyguard.ui.widget.CommonPopupView
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
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.set

fun Activity.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Fragment.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.activity, msg, duration).show()
}

fun Activity.dialog(msg: CharSequence) {
    XPopup.Builder(this).asCustom(asNormal(this, msg.toString())).show()
}

fun Fragment.dialog(msg: CharSequence) {
    XPopup.Builder(this.activity).asCustom(asNormal(this.activity!!, msg.toString())).show()
}

fun asNormal(
    context: Context,
    title: String?,
    positiveTitle: String? = "确定",
    confirmListener: OnConfirmListener? = null,
    negativeTitle: String? = "取消",
    cancelListener: OnCancelListener? = null,
    isHideCancel: Boolean? = true
): CommonPopupView? {
    return CommonPopupView(context).apply {
        setTitleContent(title)
        setCancelText(negativeTitle)
        setConfirmText(positiveTitle)
        setListener(confirmListener, cancelListener)
        if (isHideCancel!!)
            hideCancelBtn()
    }
}

fun Activity.launchMain() =
    this.apply {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

fun Fragment.launchAddDevice() =
    this.apply {
        startActivity(Intent(activity, AddDeviceActivity::class.java))
    }

fun Fragment.launchLogin() =
    this.apply {
        startActivity(
            Intent(activity, LoginActivity::class.java).apply {
                putExtra(
                    Constants.KEY_LOGIN,
                    true
                )
            })
        activity!!.finish()
    }

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value
fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t
fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply { postValue(t) }

fun <T> Observable<T>.sync(): Observable<T> =
    this.subscribeOn(AndroidSchedulers.mainThread()).observeOn(
        AndroidSchedulers.mainThread()
    )

fun <T> Observable<T>.async(): Observable<T> =
    this.subscribeOn(Schedulers.io()).observeOn(
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

fun <T : BaseResponse<*>> Single<T>.handleResult(): Single<T> {
    return this.compose { upstream ->
        upstream.flatMap {
            if (it.Status == "0") {
                return@flatMap Single.just(it)
            } else {
                return@flatMap Single.error<T>(Throwable(it.Message))
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

@BindingAdapter(value = ["app:secondColor", "app:startPos", "app:endPos"], requireAll = false)
fun secondColor(
    view: TextView,
    color: Int,
    start: Int,
    end: Int
) {
    view.text?.let {
        view.text = it.formatStringColor(
            view.context,
            color,
            start,
            end
        )
    }
}

private fun CharSequence.setSpan(span: ParcelableSpan, start: Int, end: Int): SpannableString {
    val spannableString = SpannableString(this)
    spannableString.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return spannableString
}

@Throws(WriterException::class)
fun String.createQRCode(widthAndHeight: Int): Bitmap? {
    val hints = Hashtable<EncodeHintType, String>()
    hints[EncodeHintType.CHARACTER_SET] = "utf-8"
    val matrix =
        MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight)
    val width = matrix.width
    val height = matrix.height
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            if (matrix[x, y]) {
                pixels[y * width + x] = -0x1000000
            }
        }
    }
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap
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

var View.topMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).topMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
    }

fun Activity.getWifiName(): String {
    val ssid = "unknown id"
    if (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return ssid
    }
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val mWifiManager =
            this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = mWifiManager.connectionInfo
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            info.ssid
        } else {
            info.ssid.replace("\"", "")
        }
    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
        val connManager =
            this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        if (networkInfo!!.isConnected) {
            if (networkInfo.extraInfo != null) {
                return networkInfo.extraInfo.replace("\"", "")
            }
        }
    }
    return ssid
}

fun String.getValue(key: String): String? {
    var returnValue = ""
    try {
        val json = JSONObject(this)
        returnValue = json.getString(key)
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return returnValue
}