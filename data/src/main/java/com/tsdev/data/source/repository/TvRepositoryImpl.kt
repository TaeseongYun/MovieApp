package com.tsdev.data.source.repository

import com.tsdev.data.constant.Language
import com.tsdev.data.local.MovieLocalSourceData
import com.tsdev.data.remote.TvRemoteSourceData
import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TVResult
import com.tsdev.data.source.TvDetailResponse
import io.reactivex.rxjava3.core.Single
import java.lang.IllegalArgumentException

internal class TvRepositoryImpl(
    private val remoteSourceData: TvRemoteSourceData,
    private val localSourceData: MovieLocalSourceData
) : TvRepository, BaseRepository<TVResult>() {

    private val defaultPage = 1

    override var nextPage: Int = defaultPage

    override fun repositoryDetailTV(apiKey: String, tvID: Int?): Single<TvDetailResponse> =
        when (currentLanguage) {
            Language.ENGLISH -> {
                remoteSourceData.remoteSourceDetailTv(tvID, apiKey, Language.ENGLISH)
            }
            Language.KOREAN -> {
                remoteSourceData.remoteSourceDetailTv(tvID, apiKey, Language.KOREAN)
            }
            else -> throw IllegalArgumentException()
        }

    override fun repositoryPopularTV(apiKey: String, loadPage: Int): Single<TVResponse> =
        when (currentLanguage) {
            Language.ENGLISH -> {
                remoteSourceData.remoteSourcePopularTV(apiKey, loadPage, Language.ENGLISH)
            }
            Language.KOREAN -> {
                remoteSourceData.remoteSourcePopularTV(apiKey, loadPage, Language.KOREAN)
            }
            else -> throw IllegalArgumentException()
        }

    override fun repositoryInputDatabase(tvResult: TVResult?) {
        localSourceData.inputTvResult(tvResult)
    }

    override fun repositoryDeleteDatabase(tvResultId: Int?) {
        localSourceData.deleteTvDatabase(tvResultId)
    }

    override fun getLoadLocalDatabase(): Single<List<TVResult>> =
        localSourceData.getLoadTvList()

    override fun repositoryGetFavoriteList(item: TVResult?): Single<Boolean> {
        return Single.just(mutableMapItem[item] ?: false)
            .flatMap {
                getLoadLocalDatabase().map {
                    mutableMapItem[item] = it.contains(item)
                    mutableMapItem[item]
                }
            }
    }

}