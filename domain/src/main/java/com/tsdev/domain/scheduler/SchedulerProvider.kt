package com.tsdev.domain.scheduler

import io.reactivex.rxjava3.core.Scheduler


interface SchedulerProvider {
    fun io(): Scheduler

    fun mainThread(): Scheduler

    fun computation(): Scheduler
}