package tsthec.tsstudy.movieapplicationmvvmstudy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.convert.GenreListConverters
import tsthec.tsstudy.movieapplicationmvvmstudy.db.convert.IntegerListConverters
import tsthec.tsstudy.movieapplicationmvvmstudy.db.tv.TvResultDAO


@Database(entities = [(MovieResult::class), (TVResult::class)], version = 4, exportSchema = false)
@TypeConverters(value = [IntegerListConverters::class, GenreListConverters::class])
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieResultDAO(): MovieResultDAO

    abstract fun tvResultDAO(): TvResultDAO

    companion object {
        private const val DB_NAME = "movie_db"

        private var instance: MovieDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }
    }
}