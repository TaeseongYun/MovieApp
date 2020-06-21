package com.tsdev.data.source.repository

import com.tsdev.data.source.TVResult
import io.reactivex.Single

interface TvRepository {
    fun repositoryDetailTV(apiKey: String, tvID: Int?)

    fun repositoryPopularTV(apiKey: String, loadPage: Int)

    fun repositoryInputDatabase(tvResult: TVResult?)

    fun repositoryDeleteDatabase(tvResult: TVResult?)

    fun getLoadLocalDatabase()

    fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean>
}