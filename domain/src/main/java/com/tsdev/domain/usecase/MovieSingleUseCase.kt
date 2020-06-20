package com.tsdev.domain.usecase

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResult
import com.tsdev.domain.scheduler.SchedulerProvider
import io.reactivex.Single

abstract class MovieSingleUseCase<in Params, T, S>(private val schedulersProvider: SchedulerProvider) {
    protected abstract fun buildUseCase(item: Params, page: Int): Single<T>

    operator fun invoke(item: Params, page: Int): Single<T> = buildUseCase(item, page)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.mainThread())

    abstract fun getDetailMovie(movieId: Int?, apiKey: Params): Single<MovieDetailResponse>

    abstract fun insertMovieDatabase(movieResult: S?)

    abstract fun getMovieDatabaseItem(movieResult: S?): Single<Boolean>

    abstract fun getMovieDatabase(): Single<List<S>>

    abstract fun getMovieDeleteDatabase(movieResult: S?)
}