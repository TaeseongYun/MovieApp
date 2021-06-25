package com.tsdev.data.remote

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import com.tsdev.data.source.MovieResult
import io.reactivex.rxjava3.core.Single

interface MovieRemoteSourceData {

    fun remoteSourceDetailMovie(
        movieId: Int?,
        language: String
    ): Single<MovieDetailResponse>

    fun remoteSourcePopularMovie(
        page: Int,
        language: String
    ): Single<List<MovieResult>>
}