package com.c0d3in3.movieapp.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


abstract class RequestInterceptor : Interceptor {
    abstract val isInternetAvailable: Boolean

    abstract fun onInternetUnavailable()

    abstract fun onInternetAvailable()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!isInternetAvailable)
            onInternetUnavailable()
        else onInternetAvailable()
        return chain.proceed(request)
    }

}