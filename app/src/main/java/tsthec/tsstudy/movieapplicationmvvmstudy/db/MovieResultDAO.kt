package tsthec.tsstudy.movieapplicationmvvmstudy.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

@Dao
interface MovieResultDAO {

    @Query("Select * from MovieResult where id = :id_")
    fun getListFavoriteMovie(id_: Int): Single<MovieResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieResult(movieResult: MovieResult): Completable
}