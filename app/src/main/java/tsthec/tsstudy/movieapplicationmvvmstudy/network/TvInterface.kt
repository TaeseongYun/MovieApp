package tsthec.tsstudy.movieapplicationmvvmstudy.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResponse

interface TvInterface {
    @GET("tv/popular")
    fun loadPopularTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String = "ko-KR",
        @Query("page") page: Int
    ): Single<TVResponse>
}