package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface

class MovieRepository private constructor(
    private val movieAPI: MovieInterface,
    private val movieRoomDatabase: MovieDatabase
) {
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

    fun repositoryMovieList(apiKey: String, language: String = "ko-KR", page: Int) =
        movieRemoteDataSource.remoteSourceMovieList(apiKey, language, page)

    fun repositoryDetailMovie(movieID: Int, apiKey: String) =
        movieRemoteDataSource.remoteSourceDetailMovie(movieID, apiKey = apiKey)

    fun repositoryPopularMovie(apiKey: String, page: Int) =
        movieRemoteDataSource.remoteSourcePopularMovie(apiKey, page)

    fun repositoryCastingMovie(movieID: Int, apiKey: String) =
        movieRemoteDataSource.remoteSourceCastingPeople(movieID, apiKey)

    fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult) =
        movieLocalDatabaseRemoteData.inputMovieResult(movieResult)

    fun repositoryMovieListbyDatabase() =
        movieLocalDatabaseRemoteData.loadMovieDatabase()
}
