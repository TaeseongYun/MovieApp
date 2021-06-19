package com.tsdev.data.network.interceptor

import com.tsdev.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlNewBuild = chain.request().url.newBuilder()
        return chain.proceed(
            chain.request().newBuilder().apply {
                addHeader(AUTH_INTERCEPTOR_KEY, BuildConfig.MOVIE_API_KEY)
                urlNewBuild.addQueryParameter(AUTH_INTERCEPTOR_KEY, BuildConfig.MOVIE_API_KEY)
            }.build()
        )
    }

    companion object {
        private const val AUTH_INTERCEPTOR_KEY = "api_key"
    }
}