package com.tsdev.domain.di

import com.tsdev.data.source.MovieResponse
import com.tsdev.domain.usecase.MovieSingleUseCase
import com.tsdev.domain.usecase.MovieUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<MovieSingleUseCase<String, MovieResponse>> { MovieUseCaseImpl(get(), get()) }
}