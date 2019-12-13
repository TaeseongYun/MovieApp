package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface

class MovieRemoteDataSource(private val movieAPI: MovieInterface) {

    fun remoteSourceMovieList(apiKey: String, language: String = "ko-KR", page: Int) =
        movieAPI.loadMovieListAboutNowPlaying(apiKey, language, page)


    fun remoteSourceDetailMovie(movieId: Int, apiKey: String, language: String = "ko-KR") =
        movieAPI.loadMovieDetailInformation(movieId, apiKey, language)

    fun remoteSourcePopularMovie(apiKey: String) =
        movieAPI.loadPopularMovie(apiKey)

    fun remoteSourceOrderByRatingMovie(apiKey: String, page: Int) =
        movieAPI.loadOrderByRatingMovie(apiKey, page = page)

    fun remoteSourceCastingPeople(movieId: Int, apiKey: String) =
        movieAPI.loadCreditCastMovie(movieId, apiKey)
}