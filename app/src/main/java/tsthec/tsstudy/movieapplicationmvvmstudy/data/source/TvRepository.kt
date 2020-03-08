package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import io.reactivex.Single
import tsthec.tsstudy.movieapplicationmvvmstudy.api.Language
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import java.util.*

class TvRepository(private val tvAPI: TvInterface, private val tvRoomDatabase: MovieDatabase) {

    private val defaultPage = 1

    internal var nextPage = defaultPage

    private val tvMutableMap = mutableMapOf<TVResult?, Boolean>()

    private val currentLanguage = Locale.getDefault().language

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
        when (currentLanguage) {
            "en" -> tvRemoteDataSource.remoteSourcePopularTV(
                apiKey,
                loadPage,
                Language.ENGLISH.language
            )
            "ko" -> tvRemoteDataSource.remoteSourcePopularTV(
                apiKey,
                loadPage,
                Language.KOREAN.language
            )
            else -> throw IllegalAccessError()
        }


    fun repositoryDetailTV(apiKey: String, tvID: Int?) =
        when (currentLanguage) {
            "en" -> tvRemoteDataSource.remoteSourceDetailTv(
                tvID, apiKey,
                Language.ENGLISH.language
            )
            "ko" -> tvRemoteDataSource.remoteSourceDetailTv(
                tvID, apiKey,
                Language.KOREAN.language
            )
            else -> throw IllegalAccessError()
        }


    fun repositoryInputDatabase(tvResult: TVResult?) {
        tvLocalDataBaseRemoteData.inputTvResult(tvResult)
        tvMutableMap[tvResult] = true
    }

    fun repositoryDeleteDatabase(tvResult: TVResult?) {
        tvLocalDataBaseRemoteData.deleteTvDatabase(tvResult?.id)
        tvMutableMap.remove(tvResult)
    }

    fun cacheClear() = tvMutableMap.clear()

    fun getLoadLocalDatabase() = tvLocalDataBaseRemoteData.getLoadTvList()

    fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean> {
        return Single.just(tvMutableMap[item] ?: false)
            .flatMap {
                getLoadLocalDatabase()
                    .map {
                        LogUtil.d(it.toString())
                        tvMutableMap[item] = it.contains(item)
                        tvMutableMap[item]
                    }
            }
    }
}