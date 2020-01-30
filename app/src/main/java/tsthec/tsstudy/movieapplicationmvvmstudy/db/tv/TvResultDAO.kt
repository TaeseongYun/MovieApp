package tsthec.tsstudy.movieapplicationmvvmstudy.db.tv

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult

@Dao
interface TvResultDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tvResult: TVResult): Completable

    @Query("Delete from TVResult where id = :id_ ")
    fun getFavoriteTvDelete(id_: Int): Completable

    @Query("Select * from TVResult")
    fun getTvListFavorite(): Single<List<TVResult>>

    @Query("Select * from TVResult where id = :id_")
    fun getFavoriteTV(id_: Int): Single<TVResult>
}