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
        language: String
    ): Single<MovieDetailResponse> {
        return movieAPI.loadMovieDetailInformation(movieId, language)
    }

    override fun remoteSourcePopularMovie(
        page: Int,
        language: String
    ): Single<MovieResponse> {
        return movieAPI.loadPopularMovie(language, page)
    }
}