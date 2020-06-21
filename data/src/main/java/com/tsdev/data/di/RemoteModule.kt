package com.tsdev.data.di

import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.remote.MovieRemoteSourceDataImpl
import com.tsdev.data.remote.TvRemoteSourceData
import com.tsdev.data.remote.TvRemoteSourceDataImpl
import org.koin.dsl.module

val remoteModule = module {
    single<MovieRemoteSourceData> { MovieRemoteSourceDataImpl(get()) }
    single<TvRemoteSourceData> { TvRemoteSourceDataImpl(get()) }
}