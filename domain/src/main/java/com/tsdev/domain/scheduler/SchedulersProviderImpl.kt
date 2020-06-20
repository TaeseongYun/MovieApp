package com.tsdev.domain.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class SchedulersProviderImpl : SchedulerProvider {
    override fun io(): Scheduler = Schedulers.io()

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()
}
