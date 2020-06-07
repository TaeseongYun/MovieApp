package com.tsdev.data.source

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TVResponse(
    val page: Int,
    val results: List<TVResult>,
    val total_pages: Int,
    val total_results: Int
)

@Parcelize
@Entity(primaryKeys = ["id"], tableName = "result")
data class TVResult(
    val backdrop_path: String?,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val vote_count: Int
) : Parcelable

@Parcelize
data class TvDetailResponse(
    val backdrop_path: String,
    val created_by: List<CreatedBy>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    val next_episode_to_air: NextEpisodeToAir,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val seasons: List<Season>,
    val status: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
): Parcelable

@Parcelize
data class CreatedBy(
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profile_path: String
): Parcelable

@Parcelize
data class LastEpisodeToAir(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
    val vote_average: Int,
    val vote_count: Int
): Parcelable

@Parcelize
data class Network(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
): Parcelable

@Parcelize
data class NextEpisodeToAir(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
    val vote_average: Int,
    val vote_count: Int
): Parcelable

@Parcelize
data class Season(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
): Parcelable