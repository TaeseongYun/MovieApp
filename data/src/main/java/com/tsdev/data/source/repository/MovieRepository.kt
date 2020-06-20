package com.tsdev.data.source.repository

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import com.tsdev.data.source.MovieResult
import io.reactivex.Single

interface MovieRepository {
    fun repositoryDeleteDatabase(movieResult: MovieResult?)

    fun loadCacheDatabaseList(movieResult: MovieResult?): Single<Boolean>

    fun repositoryGetListByDatabase(): Single<List<MovieResult>>

    fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?)

    fun repositoryPopularMovie(apiKey: String, loadPage: Int): Single<MovieResponse>

    fun repositoryDetailMovie(movieID: Int?, apiKey: String): Single<MovieDetailResponse>

    var nextPage: Int
}