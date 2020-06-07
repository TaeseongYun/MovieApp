package com.tsdev.data.di

import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.data.source.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}