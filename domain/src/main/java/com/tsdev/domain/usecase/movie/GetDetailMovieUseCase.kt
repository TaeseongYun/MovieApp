package com.tsdev.domain.usecase.movie

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

internal class GetDetailMovieUseCase(
    private val movieRepository: MovieRepository,
    schedulerProvider: SchedulerProvider
) : MovieSingleUseCase<Int, MovieDetailResponse>(schedulerProvider) {

    override fun buildUseCase(params: Int): Flowable<MovieDetailResponse> {
        //todo UseCase 적용
        return movieRepository.repositoryDetailMovie(params).toFlowable()
    }

}