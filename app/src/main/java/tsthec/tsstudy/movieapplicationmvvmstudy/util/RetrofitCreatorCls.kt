package tsthec.tsstudy.movieapplicationmvvmstudy.util

import android.os.Build
import okhttp3.Interceptor
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
//            //디버깅일때만 로그가 찍힘
//            if (BuildConfig.DEBUG)
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            else
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        )
        connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
    }.build()

//fun createOkHttpClient(isInternetAvailable: () -> Boolean): OkHttpClient =
//    OkHttpClient.Builder().apply {
//        addInterceptor(
//            HttpLoggingInterceptor().setLevel(
//                if (BuildConfig.DEBUG)
//                    HttpLoggingInterceptor.Level.BODY
//                else HttpLoggingInterceptor.Level.NONE
//            )
//        )
//        connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
//        writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
//        readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
//        addInterceptor(networkCheckInterceptor(isInternetAvailable))
//    }.build()

//fun networkCheckInterceptor(isInternetAvailable: () -> Boolean) = Interceptor { chain ->
//    chain.run {
//        if (!isInternetAvailable()) {
//            throw NoNetworkException("Is not available network!!!")
//        }
//        proceed(chain.request())
//    }
//}
//
//class NoNetworkException(message: String) : Throwable(message)