package com.tsdev.data.source.repository

import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.MovieRemoteSourceData
import com.tsdev.data.source.TVResult
import io.reactivex.Single

internal class TvRepositoryImpl(private val remoteSourceData: MovieRemoteSourceData,
                                private val localSourceData: MovieLocalSourceData) : TvRepository {
    override fun repositoryDetailTV(apiKey: String, tvID: Int?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun repositoryPopularTV(apiKey: String, loadPage: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun repositoryInputDatabase(tvResult: TVResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun repositoryDeleteDatabase(tvResult: TVResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLoadLocalDatabase() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}