package com.tsdev.data.di

import com.tsdev.data.db.AppDataBase
import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.local.MovieLocalSourceDataImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    single { AppDataBase.getInstance(androidContext()) }

    single { get<AppDataBase>().movieResultDAO() }

    single { get<AppDataBase>().tvResultDAO() }

    single<MovieLocalSourceData> { MovieLocalSourceDataImpl(get(), get()) }
}