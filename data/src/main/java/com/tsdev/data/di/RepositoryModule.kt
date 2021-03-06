package com.tsdev.data.di

import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.data.source.repository.MovieRepositoryImpl
import com.tsdev.data.source.repository.TvRepository
import com.tsdev.data.source.repository.TvRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(named("pagingFactory")), get(named("pagingConfig")), get(), get()) }
    single<TvRepository> { TvRepositoryImpl(get(), get()) }
}