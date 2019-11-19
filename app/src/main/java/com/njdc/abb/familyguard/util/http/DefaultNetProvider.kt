package com.njdc.abb.familyguard.util.http


import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.njdc.abb.familyguard.BuildConfig
import okhttp3.*
import java.io.IOException
import java.util.*

class DefaultNetProvider(private val mContext: Context) : NetProvider {

    companion object {

        const val CONNECT_TIME_OUT: Long = 20
        const val READ_TIME_OUT: Long = 180
        const val WRITE_TIME_OUT: Long = 30
    }

    override fun configInterceptors(): Array<Interceptor>? {
        return null
    }

    override fun configHttps(builder: OkHttpClient.Builder) {

    }

    override fun configCookie(): CookieJar? =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(mContext))

    override fun configHandler(): RequestHandler? = HeaderHandler()

    override fun configConnectTimeoutSecs(): Long = CONNECT_TIME_OUT

    override fun configReadTimeoutSecs(): Long = READ_TIME_OUT

    override fun configWriteTimeoutSecs(): Long = WRITE_TIME_OUT

    override fun configLogEnable(): Boolean? = BuildConfig.DEBUG

    private val traceId: String
        get() = UUID.randomUUID().toString()

    inner class HeaderHandler : RequestHandler {

        override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
            return request
        }

        @Throws(IOException::class)
        override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
            return response
        }
    }
}
