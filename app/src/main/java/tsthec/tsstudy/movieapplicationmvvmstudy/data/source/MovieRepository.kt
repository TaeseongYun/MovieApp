package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.api.Language
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface
import java.util.*

class MovieRepository private constructor(
    private val movieAPI: MovieInterface,
    private val movieRoomDatabase: MovieDatabase
) {

    private val defaultPage = 1

    internal var nextPage = defaultPage

    private val movieCacheMap = mutableMapOf<MovieResult?, Boolean>()

    private val currentLanguage = Locale.getDefault().language

    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(movieAPI: MovieInterface, movieRoomDatabase: MovieDatabase) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(movieAPI, movieRoomDatabase).also { instance = it }
            }
    }

    private val movieLocalDatabaseRemoteData: MovieLocalDatabaseRemoteData by lazy {
        MovieLocalDatabaseRemoteData(movieRoomDatabase)
    }

    private val movieRemoteDataSource: MovieRemoteDataSource by lazy {
        MovieRemoteDataSource(movieAPI)
    }

    fun repositoryDetailMovie(movieID: Int?, apiKey: String) =
        when (currentLanguage) {
            "en" -> movieRemoteDataSource.remoteSourceDetailMovie(
                movieID,
                apiKey,
                Language.ENGLISH.language
            )
            "ko" -> movieRemoteDataSource.remoteSourceDetailMovie(
                movieID,
                apiKey,
                Language.KOREAN.language
            )
            else -> throw IllegalAccessException()
        }

    fun repositoryPopularMovie(apiKey: String, loadPage: Int) =
        when (currentLanguage) {
            "en" -> movieRemoteDataSource.remoteSourcePopularMovie(
                apiKey,
                loadPage,
                Language.ENGLISH.language
            )
            "ko" -> movieRemoteDataSource.remoteSourcePopularMovie(
                apiKey,
                loadPage,
                Language.KOREAN.language
            )
            else -> throw IllegalAccessException()
        }


    fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult?) {
        movieLocalDatabaseRemoteData.inputMovieResult(movieResult)
        movieCacheMap[movieResult] = true
    }

    fun repositoryGetListByDatabase() =
        movieLocalDatabaseRemoteData.loadMovieDatabaseList()

    fun loadCacheDatabaseList(movieResult: MovieResult?): Single<Boolean> {
        return Single.just(movieCacheMap[movieResult] ?: false)
            .flatMap {
                repositoryGetListByDatabase().map {
                    movieCacheMap[movieResult] = it.contains(movieResult)
                    movieCacheMap[movieResult]
                }
            }
    }

    fun repositoryDeleteDatabase(movieResult: MovieResult?) {
        movieLocalDatabaseRemoteData.deleteMovieDatabase(movieResult?.id)
        movieCacheMap.remove(movieResult)
    }

}
