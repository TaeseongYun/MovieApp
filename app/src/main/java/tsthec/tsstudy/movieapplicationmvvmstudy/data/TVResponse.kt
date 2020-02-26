package tsthec.tsstudy.movieapplicationmvvmstudy.data

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