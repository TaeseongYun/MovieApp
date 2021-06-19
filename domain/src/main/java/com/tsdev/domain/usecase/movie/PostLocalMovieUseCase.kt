package com.tsdev.domain.usecase.movie

import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.MovieCompletableUseCase
import io.reactivex.rxjava3.core.Completable

internal class PostLocalMovieUseCase(
    private val movieRepository: MovieRepository,
    schedulerProvider: SchedulerProvider
) : MovieCompletableUseCase<MovieResult>(schedulerProvider) {

    override fun build(params: MovieResult): Completable {
        return movieRepository.repositoryMovieInsertRoomDatabase(params)
    }

}