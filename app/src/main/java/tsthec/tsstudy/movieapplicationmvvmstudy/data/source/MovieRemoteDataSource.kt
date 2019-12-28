package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface

class MovieRemoteDataSource(private val movieAPI: MovieInterface) {

    fun remoteSourceDetailMovie(movieId: Int, apiKey: String, language: String = "ko-KR") =
        movieAPI.loadMovieDetailInformation(movieId, apiKey, language)

    fun remoteSourcePopularMovie(apiKey: String, page: Int) =
        movieAPI.loadPopularMovie(apiKey, page = page)

    fun remoteSourceOrderByRatingMovie(apiKey: String, page: Int) =
        movieAPI.loadOrderByRatingMovie(apiKey, page = page)

    fun remoteSourceCastingPeople(movieId: Int, apiKey: String) =
        movieAPI.loadCreditCastMovie(movieId, apiKey)

    fun remoteSourcePopularTV(apiKey: String, page: Int) =
        movieAPI.loadPopularTv(apiKey, page = page)
}