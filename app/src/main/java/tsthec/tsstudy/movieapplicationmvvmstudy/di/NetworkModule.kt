package tsthec.tsstudy.movieapplicationmvvmstudy.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.util.clientOkHttp
import java.util.concurrent.TimeUnit

private const val REQUEST_TIME_OUT = 60L

val networkModule = module {
    single {
        OkHttpClient.Builder().apply {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else
                        HttpLoggingInterceptor.Level.NONE
                }
            )
            connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        }.build()
    }

    single {
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }
}