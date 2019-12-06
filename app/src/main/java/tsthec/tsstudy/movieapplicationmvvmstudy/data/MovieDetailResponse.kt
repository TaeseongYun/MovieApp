package tsthec.tsstudy.movieapplicationmvvmstudy.data

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val adult: Boolean,
    val backdrop_path: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    @SerializedName("tagline")
    val tagLine: String,
    val title: String,
    val video: Boolean,
    val vote_average: Int,
    val vote_count: Int
)

data class BelongsToCollection(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val poster_path: String
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
)