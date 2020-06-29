package com.tsdev.data.source.repository

import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TVResult
import com.tsdev.data.source.TvDetailResponse
import io.reactivex.Single

interface TvRepository {
    fun repositoryDetailTV(apiKey: String, tvID: Int?): Single<TvDetailResponse>

    fun repositoryPopularTV(apiKey: String, loadPage: Int): Single<TVResponse>

    fun repositoryInputDatabase(tvResult: TVResult?)

    fun repositoryDeleteDatabase(tvResultId: Int?)

    fun getLoadLocalDatabase(): Single<List<TVResult>>

    fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean>

    var nextPage: Int
}