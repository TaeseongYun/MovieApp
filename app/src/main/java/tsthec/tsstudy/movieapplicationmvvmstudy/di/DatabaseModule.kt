package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase

val databaseModel = module {
    single { MovieDatabase.getInstance(androidApplication()) }
    single { get<MovieDatabase>().movieResultDAO() }
}