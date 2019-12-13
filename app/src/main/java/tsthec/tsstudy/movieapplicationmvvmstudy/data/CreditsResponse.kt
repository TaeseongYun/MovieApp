package tsthec.tsstudy.movieapplicationmvvmstudy.data

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)

data class Cast(
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("profile_path")
    val profilePath: String?
)

data class Crew(
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    val profile_path: String
)