package com.tsdev.data.remote

import com.tsdev.data.network.MovieNetworkInterface
import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import io.reactivex.rxjava3.core.Single

internal class MovieRemoteSourceDataImpl(
    private val movieAPI: MovieNetworkInterface
) : MovieRemoteSourceData {

    override fun remoteSourceDetailMovie(
        movieId: Int?,
        apiKey: String,
        language: String
    ): Single<MovieDetailResponse> {
        return movieAPI.loadMovieDetailInformation(
            movieId,
            apiKey,
            language
        )
    }

    override fun remoteSourcePopularMovie(
        apiKey: String,
        page: Int,
        language: String
    ): Single<MovieResponse> {
        return movieAPI.loadPopularMovie(apiKey, language, page)
    }
}