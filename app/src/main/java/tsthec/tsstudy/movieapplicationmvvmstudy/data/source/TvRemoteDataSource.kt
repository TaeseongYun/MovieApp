package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface

class TvRemoteDataSource(private val tvAPI: TvInterface) {

    fun remoteSourcePopularTV(apiKey: String, page: Int) =
        tvAPI.loadPopularTv(apiKey, page = page)
}