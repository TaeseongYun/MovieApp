package tsthec.tsstudy.movieapplicationmvvmstudy.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

@Dao
interface MovieResultDAO {
    @Query("Select * from MovieResult where id = :id_")
    fun getFavoriteMovie(id_: Int?): Single<MovieResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieResult(movieResult: MovieResult?)

    @Query("Select * from MovieResult")
    fun getListFavorite(): Single<List<MovieResult>>

    @Query("Delete from MovieResult Where id = :id_")
    fun getFavoriteMovieDelete(id_: Int?)
}