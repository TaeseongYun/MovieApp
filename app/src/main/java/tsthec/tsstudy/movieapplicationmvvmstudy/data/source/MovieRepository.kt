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

    fun repositoryDetailMovie(movieID: Int, apiKey: String) =
        movieRemoteDataSource.remoteSourceDetailMovie(movieID, apiKey = apiKey)

    fun repositoryPopularMovie(apiKey: String, page: Int) =
        movieRemoteDataSource.remoteSourcePopularMovie(apiKey, page)

    fun repositoryCastingMovie(movieID: Int, apiKey: String) =
        movieRemoteDataSource.remoteSourceCastingPeople(movieID, apiKey)

    fun repositoryMovieInsertRoomDatabase(movieResult: MovieResult) =
        movieLocalDatabaseRemoteData.inputMovieResult(movieResult)

    fun repositoryGetDetailMovie(paramsID: Int) =
        movieLocalDatabaseRemoteData.loadMovieDatabase(paramsID)

    fun repositoryGetListbyDatabase() =
        movieLocalDatabaseRemoteData.loadMovieDatabaseList()

    fun repositoryDeleteDatabase(paramsID: Int) =
        movieLocalDatabaseRemoteData.deleteMovieDatabase(paramsID)

    fun repositoryLoadPopularTV(apiKey: String, page: Int) =
        movieRemoteDataSource.remoteSourcePopularTV(apiKey, page)
}
