package com.tsdev.data.db.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tsdev.data.source.MovieResult
import io.reactivex.Single

@Dao
interface MovieDAO {
    @Query("Select * from MovieResult where id = :id_")
    fun getFavoriteMovie(id_: Int?): Single<MovieResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieResult(movieResult: MovieResult?)

    @Query("Select * from MovieResult")
    fun getListFavorite(): Single<List<MovieResult>>

    @Query("Delete from MovieResult Where id = :id_")
    fun getFavoriteMovieDelete(id_: Int?)
}