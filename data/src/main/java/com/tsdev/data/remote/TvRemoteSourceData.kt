package com.tsdev.data.remote

import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TvDetailResponse
import io.reactivex.Single

interface TvRemoteSourceData {
    fun remoteSourcePopularTV(apiKey: String, page: Int, language: String): Single<TVResponse>

    fun remoteSourceDetailTv(tvId: Int?, apiKey: String, language: String): Single<TvDetailResponse>
}