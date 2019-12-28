package tsthec.tsstudy.movieapplicationmvvmstudy.data

import com.google.gson.annotations.SerializedName

data class TVResponse(
    val page: Int,
    val results: List<TVResult>,
    val total_pages: Int,
    val total_results: Int
)

data class TVResult(
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    val vote_average: Double,
    val vote_count: Int
)