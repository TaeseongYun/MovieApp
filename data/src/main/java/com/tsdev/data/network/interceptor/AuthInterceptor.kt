package com.tsdev.data.network.interceptor

import com.tsdev.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val customUrl = request.url.newBuilder().addQueryParameter(AUTH_INTERCEPTOR_KEY, BuildConfig.MOVIE_API_KEY).build()
        return chain.proceed(
            request.newBuilder().apply {
                addHeader(AUTH_INTERCEPTOR_KEY, BuildConfig.MOVIE_API_KEY)
                url(customUrl)
            }.build()
        )
    }

    companion object {
        private const val AUTH_INTERCEPTOR_KEY = "api_key"
    }
}