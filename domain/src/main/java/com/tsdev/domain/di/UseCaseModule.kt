package com.tsdev.domain.di

import com.tsdev.data.source.*
import com.tsdev.domain.usecase.MovieSingleUseCase
import com.tsdev.domain.usecase.MovieUseCaseImpl
import com.tsdev.domain.usecase.TvSingleUseCase
import com.tsdev.domain.usecase.TvUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<MovieSingleUseCase<String, MovieResponse, MovieResult>> {
        MovieUseCaseImpl(
            get(),
            get()
        )
    }
    single<TvSingleUseCase<String, TVResponse, TVResult>> { TvUseCaseImpl(get(), get()) }
}