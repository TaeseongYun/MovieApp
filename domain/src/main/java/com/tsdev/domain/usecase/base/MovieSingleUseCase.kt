package com.tsdev.domain.usecase.base

import com.tsdev.domain.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Single

abstract class MovieSingleUseCase<in Params, T>(private val schedulersProvider: SchedulerProvider) {
    protected abstract fun buildUseCase(params: Params): Single<T>

    operator fun invoke(item: Params): Single<T> = buildUseCase(item)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.mainThread())

//    abstract fun getDetailMovie(movieId: Int?, apiKey: Params): Single<MovieDetailResponse>
//
//    abstract fun insertMovieDatabase(movieResult: S?)
//
//    abstract fun getMovieDatabaseItem(movieResult: S?): Single<Boolean>
//
//    abstract fun getMovieDatabase(): Single<List<S>>
//
//    abstract fun getMovieDeleteDatabase(movieResult: S?)
}