package tsthec.tsstudy.movieapplicationmvvmstudy.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import java.util.concurrent.TimeUnit


private const val REQUEST_TIME_OUT = 60L

fun <T> createRetrofitFun(
    baseUrl: String,
    cls: Class<T>
): T =
    Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(clientOkHttp())
        .build()
        .create(cls)


//Json 받아온 결과를 콘솔창에다 찍어줌
fun clientOkHttp(): OkHttpClient =
    OkHttpClient.Builder().apply {
        addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else
                    HttpLoggingInterceptor.Level.NONE
            )
        )
        connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
    }.build()