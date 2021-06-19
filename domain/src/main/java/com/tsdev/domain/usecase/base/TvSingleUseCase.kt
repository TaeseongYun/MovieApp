package com.tsdev.domain.usecase.base

import com.tsdev.data.source.TvDetailResponse
import com.tsdev.domain.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Single

abstract class TvSingleUseCase<in Params, T, S>(private val schedulersProvider: SchedulerProvider) {
    protected abstract fun buildUseCase(item: Params, loadPage: Int): Single<T>

    operator fun invoke(item: Params, loadPage: Int): Single<T> = buildUseCase(item, loadPage)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.mainThread())

    abstract fun detailTV(apiKey: Params, tvID: Int?): Single<TvDetailResponse>

    abstract fun inputDatabase(tvResult: S?)

    abstract fun deleteDatabase(tvResult: S?)

    abstract fun getLoadDatabases(): Single<List<S>>

    abstract fun getFavoriteList(item: S?): Single<Boolean>
}