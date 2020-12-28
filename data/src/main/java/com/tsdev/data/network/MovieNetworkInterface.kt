package com.tsdev.data.network

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieNetworkInterface {
    @GET("movie/{movie_id}")
    fun loadMovieDetailInformation(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Single<MovieDetailResponse>

    @GET("movie/popular")
    fun loadPopularMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<MovieResponse>
}