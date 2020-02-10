package tsthec.tsstudy.movieapplicationmvvmstudy.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MultiSearchResponse

interface SearchInterface {
    @GET
    fun multiSearch(
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY,
        @Query("language") language: String = "ko-KR",
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<MultiSearchResponse>
}