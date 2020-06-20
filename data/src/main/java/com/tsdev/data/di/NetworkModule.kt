package com.tsdev.data.di

import com.tsdev.data.BuildConfig
import com.tsdev.data.network.MovieNetworkInterface
import com.tsdev.data.network.TvNetworkInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val baseURL = "https://api.themoviedb.org/3/"

private const val REQUEST_TIME_OUT = 60L

val networkModule = module {
    single {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }


    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single { get<Retrofit>().create(MovieNetworkInterface::class.java) }
    single { get<Retrofit>().create(TvNetworkInterface::class.java) }
}