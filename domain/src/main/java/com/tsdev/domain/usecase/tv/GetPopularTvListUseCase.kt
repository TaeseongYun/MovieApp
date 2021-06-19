package com.tsdev.domain.usecase.tv

import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TVResult
import com.tsdev.data.source.TvDetailResponse
import com.tsdev.data.source.repository.TvRepository
import com.tsdev.domain.scheduler.SchedulerProvider
import com.tsdev.domain.usecase.base.TvSingleUseCase
import io.reactivex.rxjava3.core.Single

internal class GetPopularTvListUseCase(
    private val tvRepository: TvRepository,
    schedulerProvider: SchedulerProvider
) : TvSingleUseCase<String, TVResponse, TVResult>(schedulerProvider) {
    override fun buildUseCase(item: String, loadPage: Int): Single<TVResponse> =
        tvRepository.repositoryPopularTV(item, loadPage)

    override fun detailTV(apiKey: String, tvID: Int?): Single<TvDetailResponse> =
        tvRepository.repositoryDetailTV(apiKey, tvID)

    override fun inputDatabase(tvResult: TVResult?) {
        tvRepository.repositoryInputDatabase(tvResult)
    }

    override fun getLoadDatabases(): Single<List<TVResult>> =
        tvRepository.getLoadLocalDatabase()

    override fun getFavoriteList(item: TVResult?): Single<Boolean> =
        tvRepository.repositoryGetFavoriteList(item)

    override fun deleteDatabase(tvResult: TVResult?) {
        tvRepository.repositoryDeleteDatabase(tvResult?.id)
    }
}