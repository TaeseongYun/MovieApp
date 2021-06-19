package com.tsdev.data.source.repository

import com.tsdev.data.constant.Language
import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.source.MovieResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

internal class MovieRepositoryImpl(
    private val movieRemoteSourceData: MovieRemoteSourceData,
    private val movieLocalSourceData: MovieLocalSourceData
) : MovieRepository, BaseRepository<MovieResult>() {

    private val defaultPage = 1

    override var nextPage = defaultPage

    override fun repositoryDeleteDatabase(movieResult: MovieResult?): Completable {
        mutableMapItem.remove(movieResult)
        return movieLocalSourceData.deleteMovieDatabase(movieResult?.id)
    }

    override fun loadCacheDatabaseList(movieResult: MovieResult?): Single<Boolean> {
        return Single.just(mutableMapItem[movieResult] ?: false)
            .flatMap {
                repositoryGetListByDatabase().map {
                    mutableMapItem[movieResult] = it.contains(movieResult)
                    mutableMapItem[movieResult]
                }
            }
    }

    override fun repositoryGetListByDatabase() = movieLocalSourceData.loadMovieDatabaseList()

    override fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?): Completable {
        return movieLocalSourceData.inputMovieResult(movieResult)
    }

    override fun repositoryPopularMovie(loadPage: Int) =
        when (currentLanguage) {
            Language.ENGLISH -> {
                movieRemoteSourceData.remoteSourcePopularMovie(nextPage, Language.ENGLISH)
            }

            Language.KOREAN -> {
                movieRemoteSourceData.remoteSourcePopularMovie(nextPage, Language.KOREAN)
            }
            else -> throw IllegalArgumentException()
        }


    override fun repositoryDetailMovie(movieID: Int?) =
        when (currentLanguage) {
            Language.ENGLISH -> {
                movieRemoteSourceData.remoteSourceDetailMovie(movieID, Language.ENGLISH)
            }

            Language.KOREAN -> {
                movieRemoteSourceData.remoteSourceDetailMovie(movieID, Language.KOREAN)
            }
            else -> throw IllegalArgumentException()
        }
}