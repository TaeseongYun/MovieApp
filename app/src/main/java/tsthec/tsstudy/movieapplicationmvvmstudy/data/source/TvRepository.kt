package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface

class TvRepository(private val tvAPI: TvInterface, private val tvRoomDatabase: MovieDatabase) {

    private val defaultPage = 1

    internal var nextPage = defaultPage

    companion object {
        private var instance: TvRepository? = null

        fun getInstance(tvAPI: TvInterface, tvRoomDatabase: MovieDatabase) =
            instance ?: synchronized(this) {
                instance ?: TvRepository(tvAPI, tvRoomDatabase).also { instance = it }
            }
    }

    private val tvCacheHashMap = mutableListOf<TVResult>()

    private val tvRemoteDataSource: TvRemoteDataSource by lazy {
        TvRemoteDataSource(tvAPI)
    }

    private val tvLocalDataBaseRemoteData: MovieLocalDatabaseRemoteData by lazy {
        MovieLocalDatabaseRemoteData(tvRoomDatabase)
    }

    fun repositoryPopularTV(apiKey: String, loadPage: Int) =
        tvRemoteDataSource.remoteSourcePopularTV(apiKey, loadPage)

    fun repositoryDetailTV(apiKey: String, tvID: Int?) =
        tvRemoteDataSource.remoteSourceDetailTv(tvID, apiKey)

    fun repositoryGetDetailDatabase(paramsID: Int?) =
        tvLocalDataBaseRemoteData.loadTVDatabase(paramsID)

    fun repositoryInputDatabase(tvResult: TVResult?) =
        tvLocalDataBaseRemoteData.inputTvResult(tvResult)

    fun repositoryDeleteDatabase(paramsID: Int?) =
        tvLocalDataBaseRemoteData.deleteTvDatabase(paramsID)

    fun repositoryGetFavoriteList()
       = tvLocalDataBaseRemoteData.getLoadTvList()
}