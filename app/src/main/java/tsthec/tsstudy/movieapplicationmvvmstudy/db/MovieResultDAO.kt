package tsthec.tsstudy.movieapplicationmvvmstudy.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

@Dao
interface MovieResultDAO {

    @Query("Select * from movieresult")
    fun getListFavoriteMovie(): Single<List<MovieResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieResult(movieResult: MovieResult): Completable
}