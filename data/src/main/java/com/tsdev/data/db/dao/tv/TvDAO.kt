package com.tsdev.data.db.dao.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tsdev.data.source.TVResult
import io.reactivex.rxjava3.core.Single

@Dao
interface TvDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tvResult: TVResult?)

    @Query("Delete from result where id = :id_ ")
    fun getFavoriteTvDelete(id_: Int?)

    @Query("Select * from result")
    fun getTvListFavorite(): Single<List<TVResult>>

    @Query("Select * from result where id = :id_")
    fun getFavoriteTV(id_: Int?): Single<TVResult>
}