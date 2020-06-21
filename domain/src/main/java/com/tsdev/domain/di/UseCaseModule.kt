package com.tsdev.domain.di

import com.tsdev.data.source.MovieResponse
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult
import com.tsdev.data.source.TvDetailResponse
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
    single<TvSingleUseCase<String, TvDetailResponse, TVResult>> { TvUseCaseImpl(get(), get()) }
}