package tsthec.tsstudy.movieapplicationmvvmstudy.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

@Dao
interface MoveReulstDAO {

    @Query("Select * from MovieResult")
    fun getListFavoriteMovie(): List<MovieResult>

    @Insert
    fun insertMovieResult(movieResult: MovieResult)


}