package com.tsdev.data.paging.datasource

import androidx.paging.PageKeyedDataSource
import com.tsdev.data.constant.Language
import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.source.MovieResult

internal class MoviePagingDataSource constructor(
    private val movieRepository: MovieRemoteSourceData
) : PageKeyedDataSource<Int, MovieResult>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieResult>
    ) {
        val initKey = 1
        movieRepository
            .remoteSourcePopularMovie(initKey, language = Language.KOREAN)
            .subscribe(
                { callback.onResult(it, null, 2) },
                {}
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        val nextKey = params.key + 1
        movieRepository.remoteSourcePopularMovie(params.key, language = Language.KOREAN)
            .subscribe(
                { callback.onResult(it, nextKey) },
                {}
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        val previousKey = params.key - 1
        movieRepository.remoteSourcePopularMovie(params.key, language = Language.KOREAN)
            .subscribe(
                { callback.onResult(it, previousKey) },
                {}
            )
    }

}