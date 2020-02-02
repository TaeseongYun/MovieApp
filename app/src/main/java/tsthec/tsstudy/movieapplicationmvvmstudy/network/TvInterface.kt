package tsthec.tsstudy.movieapplicationmvvmstudy.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TvDetailResponse

interface TvInterface {
    @GET("tv/popular")
    fun loadPopularTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String = "ko-KR",
        @Query("page") page: Int
    ): Single<TVResponse>

    @GET("tv/{tv_id}")
    fun getDetailTv(
        @Path("tv_id") tvID: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "ko-KR"
    ): Single<TvDetailResponse>
}