package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface

class MovieRemoteDataSource(private val movieAPI: MovieInterface) {

    fun remoteSourceDetailMovie(movieId: Int?, apiKey: String, language: String) =
        movieAPI.loadMovieDetailInformation(movieId, apiKey, language)

    fun remoteSourcePopularMovie(apiKey: String, page: Int, language: String) =
        movieAPI.loadPopularMovie(apiKey, language, page)
}