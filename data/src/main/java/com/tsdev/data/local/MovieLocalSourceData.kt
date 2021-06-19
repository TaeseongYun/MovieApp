package com.tsdev.data.local

import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MovieLocalSourceData {
    fun deleteMovieDatabase(paraID: Int?): Completable

    fun loadMovieDatabaseList(): Single<List<MovieResult>>

    fun inputMovieResult(movieResult: MovieResult?): Completable

    fun inputTvResult(tvResult: TVResult?)

    fun deleteTvDatabase(paraID: Int?)

    fun getLoadTvList(): Single<List<TVResult>>
}