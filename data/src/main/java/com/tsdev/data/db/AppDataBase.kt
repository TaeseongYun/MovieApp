package com.tsdev.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tsdev.data.db.convert.GenreConverterProvider
import com.tsdev.data.db.convert.IntListConverterProvider
import com.tsdev.data.db.dao.movie.MovieDAO
import com.tsdev.data.db.dao.tv.TvDAO
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult

@Database(version = 1, entities = [MovieResult::class, TVResult::class])
@TypeConverters(value = [IntListConverterProvider::class, GenreConverterProvider::class])
abstract class AppDataBase: RoomDatabase() {

    abstract fun movieResultDAO(): MovieDAO

    abstract fun tvResultDAO(): TvDAO

    companion object {
        private const val DB_NAME = "movie_db"

        private var instance: AppDataBase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }
    }
}