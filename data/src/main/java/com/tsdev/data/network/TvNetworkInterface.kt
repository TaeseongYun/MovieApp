package com.tsdev.data.network

import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TvDetailResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvNetworkInterface {
    @GET("tv/popular")
    fun loadPopularTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<TVResponse>

    @GET("tv/{tv_id}")
    fun getDetailTv(
        @Path("tv_id") tvID: Int?,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Single<TvDetailResponse>
}