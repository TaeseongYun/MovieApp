package com.tsdev.data.di

import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.data.source.repository.MovieRepositoryImpl
import com.tsdev.data.source.repository.TvRepository
import com.tsdev.data.source.repository.TvRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<TvRepository> { TvRepositoryImpl(get(), get()) }
}