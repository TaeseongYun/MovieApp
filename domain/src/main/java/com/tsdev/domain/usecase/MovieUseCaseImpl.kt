package com.tsdev.domain.usecase

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import io.reactivex.Single

internal class MovieUseCaseImpl(
    private val movieRepository: MovieRepository,
    schedulersProvider: SchedulerProvider
) :
    MovieSingleUseCase<String, MovieResponse>(schedulersProvider) {

    override fun buildUseCase(item: String, page: Int): Single<MovieResponse> =
        movieRepository.repositoryPopularMovie(item, page)

    override fun getDetailMovie(movieId: Int, apiKey: String): Single<MovieDetailResponse> {
        return movieRepository.repositoryDetailMovie(movieId, apiKey)
    }
}