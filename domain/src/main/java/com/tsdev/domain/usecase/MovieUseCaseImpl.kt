package com.tsdev.domain.usecase

import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResponse
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import io.reactivex.Single

internal class MovieUseCaseImpl(
    private val movieRepository: MovieRepository,
    schedulersProvider: SchedulerProvider
) :
    MovieSingleUseCase<String, MovieResponse, MovieResult>(schedulersProvider) {

    override fun buildUseCase(item: String, page: Int): Single<MovieResponse> =
        movieRepository.repositoryPopularMovie(item, page)

    override fun getDetailMovie(movieId: Int?, apiKey: String): Single<MovieDetailResponse> {
        return movieRepository.repositoryDetailMovie(movieId, apiKey)
    }

    override fun insertMovieDatabase(movieResult: MovieResult?) {
        movieRepository.repositoryMovieInsertRoomDatabase(movieResult)
    }

    override fun getMovieDatabaseItem(movieResult: MovieResult?): Single<Boolean> {
        return movieRepository.loadCacheDatabaseList(movieResult)
    }

    override fun getMovieDatabase(): Single<List<MovieResult>> {
        return  movieRepository.repositoryGetListByDatabase()
    }

    override fun getMovieDeleteDatabase(movieResult: MovieResult?) {
        movieRepository.repositoryDeleteDatabase(movieResult)
    }
}