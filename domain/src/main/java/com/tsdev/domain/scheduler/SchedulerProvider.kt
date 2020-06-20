package com.tsdev.domain.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler

    fun mainThread(): Scheduler

    fun computation(): Scheduler
}