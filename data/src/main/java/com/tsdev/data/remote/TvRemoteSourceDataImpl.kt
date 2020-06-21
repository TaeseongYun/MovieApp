package com.tsdev.data.remote

import com.tsdev.data.network.TvNetworkInterface

internal class TvRemoteSourceDataImpl(private val tvAPI: TvNetworkInterface) : TvRemoteSourceData {
    override fun remoteSourcePopularTV(apiKey: String, page: Int, language: String) =
        tvAPI.loadPopularTv(apiKey, language, page)


    override fun remoteSourceDetailTv(tvId: Int?, apiKey: String, language: String) =
        tvAPI.getDetailTv(tvId, apiKey, language)
}