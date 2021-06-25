package com.tsdev.domain.usecase.movie

import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

internal class GetLocalMovieUseCase(
    private val movieRepository: MovieRepository,
    schedulerProvider: SchedulerProvider
) : MovieSingleUseCase<MovieResult?, Boolean>(schedulerProvider) {

    override fun buildUseCase(params: MovieResult?): Flowable<Boolean> {
        return movieRepository.loadCacheDatabaseList(params).toFlowable()
    }

}