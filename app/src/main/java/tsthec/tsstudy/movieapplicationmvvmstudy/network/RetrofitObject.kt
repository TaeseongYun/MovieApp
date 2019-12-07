package tsthec.tsstudy.movieapplicationmvvmstudy.network

import tsthec.tsstudy.movieapplicationmvvmstudy.MovieApp
import tsthec.tsstudy.movieapplicationmvvmstudy.util.createRetrofitFun
import tsthec.tsstudy.movieapplicationmvvmstudy.util.isOnline

object RetrofitObject {
    private const val baseURL = "https://api.themoviedb.org/3/"

    val movieAPI: MovieInterface by lazy {
        createRetrofitFun(
            baseURL,
            MovieInterface::class.java
        )
    }
}