package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface

class TvRepository(private val tvAPI: TvInterface) {

    var tvPage = 1

    companion object {
        var instance: TvRepository? = null

        fun getInstance(tvAPI: TvInterface) =
            instance ?: synchronized(this) {
                instance ?: TvRepository(tvAPI).also { instance = it }
            }
    }

    private val tvRemoteDataSource: TvRemoteDataSource by lazy {
        TvRemoteDataSource(tvAPI)
    }

    fun repositoryPopularTV(apiKey: String) =
        tvRemoteDataSource.remoteSourcePopularTV(apiKey, tvPage)
}