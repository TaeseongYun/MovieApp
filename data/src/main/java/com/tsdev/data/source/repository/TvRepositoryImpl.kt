package com.tsdev.data.source.repository

import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.MovieRemoteSourceData

internal class TvRepositoryImpl(private val remoteSourceData: MovieRemoteSourceData,
                                private val localSourceData: MovieLocalSourceData) : TvRepository {

}