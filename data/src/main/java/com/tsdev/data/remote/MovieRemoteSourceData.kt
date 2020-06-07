package com.tsdev.data.remote

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import io.reactivex.Single

interface MovieRemoteSourceData {

    fun remoteSourceDetailMovie(
        movieId: Int?,
        apiKey: String,
        language: String
    ): Single<MovieDetailResponse>

    fun remoteSourcePopularMovie(
        apiKey: String,
        page: Int,
        language: String
    ): Single<MovieResponse>
}