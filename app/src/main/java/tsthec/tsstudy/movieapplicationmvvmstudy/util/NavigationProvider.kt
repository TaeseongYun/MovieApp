package tsthec.tsstudy.movieapplicationmvvmstudy.util

import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult

class NavigationProvider private constructor(
    private val tvResult: TVResult,
    private val movieResult: MovieResult
) {

    companion object {
        var instance: NavigationProvider? = null

        fun getInstance(tvResult: TVResult, movieResult: MovieResult) =
            instance ?: synchronized(this) {
                instance ?: NavigationProvider(tvResult, movieResult)
            }
    }

    val detailTvNavArgs
        get() = tvResult

    val detailMovieNavArgs
        get() = movieResult
}