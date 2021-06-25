package com.tsdev.data.source.repository

import androidx.paging.PagedList
import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun repositoryDeleteDatabase(movieResult: MovieResult?): Completable

    fun loadCacheDatabaseList(movieResult: MovieResult?): Single<Boolean>

    fun repositoryGetListByDatabase(): Single<List<MovieResult>>

    fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?): Completable

    fun repositoryPopularMovie(loadPage: Int): Flowable<PagedList<MovieResult>>

    fun repositoryDetailMovie(movieID: Int?): Single<MovieDetailResponse>

    var nextPage: Int
}