package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailTVInformationViewModel(
    private val tvRepository: TvRepository
) : BaseLifeCycleViewModel<TVResult>() {

    private val tvMutableMap = mutableMapOf<TVResult, Boolean>()

    val tvGenreMutableLiveData = MutableLiveData<List<Genre>>()

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    init {
        disposable += tvRepository.repositoryGetFavoriteList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tvResultList ->
                    LogUtil.d("$tvResultList")
                    tvResultList.forEach {
                        tvMutableMap[it] = true
                    }
                    LogUtil.d("$tvMutableMap")
                }, { it.printStackTrace() }
            )

    }


//    override fun onFavoriteButtonClicked(item: TVResult?) {
//
//    }

    override fun onDeleteFavoriteButtonClicked(item: TVResult?) {
        item?.let { tvMutableMap[it] = false }
        databaseSubject.onNext(
            Pair(
                { tvRepository.repositoryDeleteDatabase(item?.id) },
                { _favoriteState.value = false }
            )
        )
    }

    fun getDetailTV(tvID: Int?) {
        disposable += tvRepository.repositoryDetailTV(
            apiKey = BuildConfig.MOVIE_API_KEY,
            tvID = tvID
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                tvGenreMutableLiveData.value = it.genres
            }, {
                it.printStackTrace()
            })
    }

    fun loadLikeState(tvResult: TVResult?) {
        when (tvMutableMap[tvResult]) {
            true -> {
                onDeleteFavoriteButtonClicked(tvResult)
            }
            false, null -> {
                onFavoriteButtonClicked(tvResult)
            }
        }
    }

    fun getLoadTVDatabase(paramsId: Int?) {
        disposable += tvRepository.repositoryGetDetailDatabase(paramsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _favoriteState.value = tvMutableMap.contains(it)
            }, {
                it.printStackTrace()
            })
    }

    override fun onFavoriteButtonClicked(item: TVResult?) {
        item?.let { tvMutableMap[it] = true }
//        tvMutableMap[item] = false
        databaseSubject.onNext(
            Pair(
                { tvRepository.repositoryInputDatabase(item) },
                { _favoriteState.value = true }
            )
        )
    }
}