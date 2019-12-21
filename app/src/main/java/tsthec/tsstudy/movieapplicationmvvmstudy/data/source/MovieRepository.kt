package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface

class MovieRepository private constructor(private val movieAPI: MovieInterface) {
    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(movieAPI: MovieInterface) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(movieAPI).also { instance = it }
            }
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
}
