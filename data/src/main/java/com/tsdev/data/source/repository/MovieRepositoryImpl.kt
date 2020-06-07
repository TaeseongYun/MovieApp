package com.tsdev.data.source.repository

import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.source.MovieResult
import io.reactivex.Single
import java.util.*

private object Const {
    const val ENGLISH = "en"

    const val KOREAN = "ko"
}

internal class MovieRepositoryImpl(
    private val movieRemoteSourceData: MovieRemoteSourceData,
    private val movieLocalSourceData: MovieLocalSourceData
) : MovieRepository {

    private val defaultPage = 1

    internal var nextPage = defaultPage

    private val movieCacheMap = mutableMapOf<MovieResult?, Boolean>()

    private val currentLanguage = Locale.getDefault().language

    override fun repositoryDeleteDatabase(movieResult: MovieResult?) {
        movieLocalSourceData.deleteMovieDatabase(movieResult?.id)
        movieCacheMap.remove(movieResult)
    }

    override fun loadCacheDatabaseList(movieResult: MovieResult?): Single<Boolean> {
        return Single.just(movieCacheMap[movieResult] ?: false)
            .flatMap {
                repositoryGetListByDatabase().map {
                    movieCacheMap[movieResult] = it.contains(movieResult)
                    movieCacheMap[movieResult]
                }
            }
    }

    override fun repositoryGetListByDatabase() = movieLocalSourceData.loadMovieDatabaseList()

    override fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?) {
        movieLocalSourceData.inputMovieResult(movieResult)
    }

    override fun repositoryPopularMovie(apiKey: String, loadPage: Int) {
        when (currentLanguage) {
            Const.ENGLISH -> {
                movieRemoteSourceData.remoteSourcePopularMovie(apiKey, nextPage, Const.ENGLISH)
            }

            Const.KOREAN -> {
                movieRemoteSourceData.remoteSourcePopularMovie(apiKey, nextPage, Const.KOREAN)
            }
        }
    }

    override fun repositoryDetailMovie(movieID: Int?, apiKey: String) {
        when (currentLanguage) {
            Const.ENGLISH -> {
                movieRemoteSourceData.remoteSourceDetailMovie(
                    movieID,
                    apiKey,
                    Const.ENGLISH
                )
            }

            Const.KOREAN -> {
                movieRemoteSourceData.remoteSourceDetailMovie(
                    movieID,
                    apiKey,
                    Const.KOREAN
                )
            }
        }

    }
}