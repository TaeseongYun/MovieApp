package com.tsdev.domain.usecase.movie

import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import io.reactivex.rxjava3.core.Single

internal class GetLocalMovieListUseCase(
    private val movieRepository: MovieRepository,
    schedulerProvider: SchedulerProvider
) : MovieSingleUseCase<Unit, List<MovieResult>>(schedulerProvider) {

    override fun buildUseCase(params: Unit): Single<List<MovieResult>> {
        return movieRepository.repositoryGetListByDatabase()
    }

}