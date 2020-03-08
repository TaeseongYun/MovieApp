package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface

class TvRemoteDataSource(private val tvAPI: TvInterface) {

    fun remoteSourcePopularTV(apiKey: String, page: Int, language: String) =
        tvAPI.loadPopularTv(apiKey, language, page)

    fun remoteSourceDetailTv(tvId: Int?, apiKey: String, language: String) =
        tvAPI.getDetailTv(tvId, apiKey, language)
}