package com.tsdev.data.source.repository

import com.tsdev.data.source.MovieResult
import io.reactivex.Single

interface MovieRepository {
    fun repositoryDeleteDatabase(movieResult: MovieResult?)

    fun loadCacheDatabaseList(movieResult: MovieResult?): Single<Boolean>

    fun repositoryGetListByDatabase(): Single<List<MovieResult>>

    fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?)

    fun repositoryPopularMovie(apiKey: String, loadPage: Int)

    fun repositoryDetailMovie(movieID: Int?, apiKey: String)
}