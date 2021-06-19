package com.tsdev.domain.usecase.movie

import com.tsdev.data.source.MovieResponse
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import com.tsdev.domain.usecase.movie.params.PopularMovieParams
import io.reactivex.rxjava3.core.Single

internal class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository,
    schedulersProvider: SchedulerProvider
) : MovieSingleUseCase<PopularMovieParams, MovieResponse>(schedulersProvider) {

    override fun buildUseCase(params: PopularMovieParams): Single<MovieResponse> {
        return movieRepository.repositoryPopularMovie(params.pages)
    }

}