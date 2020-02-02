package tsthec.tsstudy.movieapplicationmvvmstudy.network



import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tsthec.tsstudy.movieapplicationmvvmstudy.data.*

interface MovieInterface {

    @GET("movie/{movie_id}")
    fun loadMovieDetailInformation(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "ko-KR"
    ): Single<MovieDetailResponse>

    @GET("movie/popular")
    fun loadPopularMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String = "ko-KR",
        @Query("page") page: Int
    ): Single<MovieResponse>

}