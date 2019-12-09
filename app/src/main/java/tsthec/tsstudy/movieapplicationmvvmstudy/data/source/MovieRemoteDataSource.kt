package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface

class MovieRemoteDataSource(private val movieAPI: MovieInterface) {

    fun remoteSourceMovieList(apiKey: String, language: String = "ko-KR", page: Int) =
        movieAPI.loadMovieListAboutNowPlaying(apiKey, language, page)


    fun remoteSourceDetailMovie(movieId: Int, apiKey: String, language: String = "ko-KR") =
        movieAPI.loadMovieDetailInformation(movieId, apiKey, language)
}