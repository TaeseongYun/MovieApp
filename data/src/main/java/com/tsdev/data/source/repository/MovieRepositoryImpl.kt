package com.tsdev.data.source.repository

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.tsdev.data.constant.Language
import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.paging.MovieDataSourceFactory
import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.source.MovieResult
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

internal class MovieRepositoryImpl(
    private val moviePagingDataSourceFactory: DataSource.Factory<Int, MovieResult>,
    private val pagingConfig: PagedList.Config,
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

    override fun repositoryPopularMovie(loadPage: Int): Flowable<PagedList<MovieResult>> {
        return when (currentLanguage) {
            Language.ENGLISH -> {
                val result = RxJavaBridge.toV3Flowable(
                    RxPagedListBuilder(
                        moviePagingDataSourceFactory,
                        pagingConfig
                    ).buildFlowable(BackpressureStrategy.BUFFER)
                )
                result
//                movieRemoteSourceData.remoteSourcePopularMovie(nextPage, Language.ENGLISH)
            }

            Language.KOREAN -> {
                val result = RxJavaBridge.toV3Flowable(
                    RxPagedListBuilder(
                        moviePagingDataSourceFactory,
                        pagingConfig
                    ).buildFlowable(BackpressureStrategy.BUFFER)
                )
                result
            }
            else -> throw IllegalArgumentException()
        }
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