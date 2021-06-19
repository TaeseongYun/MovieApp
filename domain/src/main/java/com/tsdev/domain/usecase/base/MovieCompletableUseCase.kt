package com.tsdev.domain.usecase.base

import com.tsdev.domain.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable

abstract class MovieCompletableUseCase<Params>(private val schedulerProvider: SchedulerProvider) {

    abstract fun build(params: Params): Completable

    operator fun invoke(params: Params): Completable {
        return build(params).subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
    }

}