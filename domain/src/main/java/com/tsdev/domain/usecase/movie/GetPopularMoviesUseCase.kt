package com.tsdev.domain.usecase.movie

import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import com.tsdev.domain.usecase.movie.params.PopularMovieParams
import io.reactivex.rxjava3.core.Flowable

internal class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository,
    schedulersProvider: SchedulerProvider
) : MovieSingleUseCase<PopularMovieParams, List<MovieResult>>(schedulersProvider) {

    override fun buildUseCase(params: PopularMovieParams): Flowable<List<MovieResult>> {
        return movieRepository.repositoryPopularMovie(params.pages).map {
            it
        }
    }

}