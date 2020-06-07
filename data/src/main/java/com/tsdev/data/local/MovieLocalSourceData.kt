package com.tsdev.data.local

import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult
import io.reactivex.Single

interface MovieLocalSourceData {
    fun deleteMovieDatabase(paraID: Int?)

    fun loadMovieDatabaseList(): Single<List<MovieResult>>

    fun inputMovieResult(movieResult: MovieResult?)

    fun inputTvResult(tvResult: TVResult?)

    fun deleteTvDatabase(paraID: Int?)

    fun getLoadTvList()
}