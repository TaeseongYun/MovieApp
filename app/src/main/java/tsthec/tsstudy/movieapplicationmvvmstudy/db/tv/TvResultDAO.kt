package tsthec.tsstudy.movieapplicationmvvmstudy.db.tv

import androidx.room.*
import io.reactivex.rxjava3.core.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult

@Dao
interface TvResultDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tvResult: TVResult?)

    @Query("Delete from result where id = :id_ ")
    fun getFavoriteTvDelete(id_: Int?)

    @Query("Select * from result")
    fun getTvListFavorite(): Single<List<TVResult>>

    @Query("Select * from result where id = :id_")
    fun getFavoriteTV(id_: Int?): Single<TVResult>
}