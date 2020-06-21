package com.tsdev.data.source.repository

import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.TvRemoteSourceData
import com.tsdev.data.source.TVResult
import io.reactivex.Single

internal class TvRepositoryImpl(
    private val remoteSourceData: TvRemoteSourceData,
    private val localSourceData: MovieLocalSourceData
) : TvRepository, BaseRepository<TVResult>() {

    override fun repositoryDetailTV(apiKey: String, tvID: Int?) {
        remoteSourceData.remoteSourceDetailTv(apiKey, tvID)
    }

    override fun repositoryPopularTV(apiKey: String, loadPage: Int) {
    }

    override fun repositoryInputDatabase(tvResult: TVResult?) {
    }

    override fun repositoryDeleteDatabase(tvResult: TVResult?) {
    }

    override fun getLoadLocalDatabase() {
    }

    override fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean> {
    }

}