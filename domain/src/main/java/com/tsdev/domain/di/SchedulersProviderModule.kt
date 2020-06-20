package com.tsdev.domain.di

import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.scheduler.SchedulersProviderImpl
import org.koin.dsl.module

val schedulersProviderModule = module {
    single<SchedulerProvider> { SchedulersProviderImpl() }
}