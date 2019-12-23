package tsthec.tsstudy.movieapplicationmvvmstudy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult


@Database(entities = [MovieResult::class], version = 1, exportSchema = false)
@TypeConverters(value = [IntegerListConverters::class])
abstract class MovieDatabase : RoomDatabase() {

    abstract fun  movieResultDAO(): MovieResultDAO

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