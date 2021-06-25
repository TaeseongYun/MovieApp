package com.tsdev.domain.di

import com.tsdev.data.source.*
import com.tsdev.domain.usecase.base.MovieCompletableUseCase
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import com.tsdev.domain.usecase.base.TvSingleUseCase
import com.tsdev.domain.usecase.movie.*
import com.tsdev.domain.usecase.movie.GetDetailMovieUseCase
import com.tsdev.domain.usecase.movie.GetLocalMovieListUseCase
import com.tsdev.domain.usecase.movie.GetLocalMovieUseCase
import com.tsdev.domain.usecase.movie.GetPopularMoviesUseCase
import com.tsdev.domain.usecase.movie.PostLocalMovieUseCase
import com.tsdev.domain.usecase.movie.params.PopularMovieParams
import com.tsdev.domain.usecase.tv.GetPopularTvListUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {

    single<TvSingleUseCase<String, TVResponse, TVResult>> { GetPopularTvListUseCase(get(), get()) }

    single<MovieCompletableUseCase<MovieResult>>(named("postLocalUseCase")) { PostLocalMovieUseCase(get(), get()) }

    single<MovieSingleUseCase<Int, MovieDetailResponse>>(named("getDetailMovie")) { GetDetailMovieUseCase(get(), get()) }

    single<MovieSingleUseCase<Unit, List<MovieResult>>>(named("getLocalMovies")) { GetLocalMovieListUseCase(get(), get()) }

    single<MovieSingleUseCase<MovieResult?, Boolean>>(named("getLocalMovie")) { GetLocalMovieUseCase(get(), get()) }

    single<MovieSingleUseCase<PopularMovieParams, List<MovieResult>>>(named("getPopularMovie")) { GetPopularMoviesUseCase(get(), get()) }

    single<MovieCompletableUseCase<MovieResult>>(named("deleteLocalUseCase")) { DeleteLocalMovieUseCase(get(), get()) }

}