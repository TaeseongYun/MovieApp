package tsthec.tsstudy.movieapplicationmvvmstudy.db

import androidx.room.RoomDatabase

abstract class MovieDatabase : RoomDatabase() {
    companion object {
       private const val DB_NAME = "movie_db"
    }


}