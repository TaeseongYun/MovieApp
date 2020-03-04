package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import io.reactivex.Maybe
import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil

class TvRepository(private val tvAPI: TvInterface, private val tvRoomDatabase: MovieDatabase) {

    private val defaultPage = 1

    internal var nextPage = defaultPage

    private val tvMutableMap = mutableMapOf<TVResult?, Boolean>()

    companion object {
        private var instance: TvRepository? = null

        fun getInstance(tvAPI: TvInterface, tvRoomDatabase: MovieDatabase) =
            instance ?: synchronized(this) {
                instance ?: TvRepository(tvAPI, tvRoomDatabase).also { instance = it }
            }
    }


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

    fun repositoryInputDatabase(tvResult: TVResult?) {
        tvLocalDataBaseRemoteData.inputTvResult(tvResult)

    }


    fun repositoryDeleteDatabase(tvResult: TVResult?) {
        tvLocalDataBaseRemoteData.deleteTvDatabase(tvResult?.id)
    }

    private fun getLoadLocalDatabase() = tvLocalDataBaseRemoteData.getLoadTvList()


    fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean> {
        tvMutableMap[item] = true
        return Single.just(tvMutableMap[item] ?: false)
            .flatMap {
                getLoadLocalDatabase()
                    .map { tvResultList ->
                        tvResultList.find { it == item }?.let {
                            tvMutableMap[it] = true
                        }
//                        if (tvMutableMap.containsKey(item))
//                            tvMutableMap[item] = false

                        tvMutableMap[item]
                    }
            }
    }
//        tvLocalDataBaseRemoteData.getLoadTvList().map {
//            LogUtil.d("왜 두번 돌지?")
//            LogUtil.d("Tv Repo Database List -> $it")
//            if (it.isNullOrEmpty())
//                tvMutableMap[item] = false
//            else {
//                it.forEach { t: TVResult ->
//                    tvMutableMap[t] = true
//                }
//            }
//            tvMutableMap
//        }

//    fun cacheLoadLocalDatabase(item: TVResult?): Maybe<Boolean> {
//        return repositoryGetFavoriteList(item).filter { !it.isNullOrEmpty() }.flatMap {
//            Maybe.just(tvMutableMap[item])
//        }
//    }
}