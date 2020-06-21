package com.tsdev.data.source.repository

import com.tsdev.data.constant.Language
import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.source.MovieResult
import io.reactivex.Single

internal class MovieRepositoryImpl(
    private val movieRemoteSourceData: MovieRemoteSourceData,
    private val movieLocalSourceData: MovieLocalSourceData
) : MovieRepository, BaseRepository<MovieResult>() {

    private val defaultPage = 1

    override var nextPage = defaultPage

    override fun repositoryDeleteDatabase(movieResult: MovieResult?) {
        movieLocalSourceData.deleteMovieDatabase(movieResult?.id)
        mutableMapItem.remove(movieResult)
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

    override fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?) {
        movieLocalSourceData.inputMovieResult(movieResult)
    }

    override fun repositoryPopularMovie(apiKey: String, loadPage: Int) =
        when (currentLanguage) {
            Language.ENGLISH -> {
                movieRemoteSourceData.remoteSourcePopularMovie(apiKey, nextPage, Language.ENGLISH)
            }

            Language.KOREAN -> {
                movieRemoteSourceData.remoteSourcePopularMovie(apiKey, nextPage, Language.KOREAN)
            }
            else -> throw IllegalArgumentException()
        }


    override fun repositoryDetailMovie(movieID: Int?, apiKey: String) =
        when (currentLanguage) {
            Language.ENGLISH -> {
                movieRemoteSourceData.remoteSourceDetailMovie(
                    movieID,
                    apiKey,
                    Language.ENGLISH
                )
            }

            Language.KOREAN -> {
                movieRemoteSourceData.remoteSourceDetailMovie(
                    movieID,
                    apiKey,
                    Language.KOREAN
                )
            }
            else -> throw IllegalArgumentException()
        }
}