package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailTVInformationViewModel(
    private val tvRepository: TvRepository
) : BaseLifeCycleViewModel<TVResult>() {

    val tvGenreMutableLiveData = MutableLiveData<List<Genre>>()

    private val _favoriteState = MutableLiveData<Boolean>(true)

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    init {
        LogUtil.d("What is value -> ${_favoriteState.value}")
        disposable += uiBehaviorSubject.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .switchMapSingle {
                tvRepository.repositoryGetFavoriteList(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteState.value = true
            }, {
                it.printStackTrace()
            })
    }

    override fun onDeleteFavoriteButtonClicked(item: TVResult?) {
        databaseSubject.onNext(
            Pair(
                { tvRepository.repositoryDeleteDatabase(item) },
                { _favoriteState.value = false }
            )
        )
        item?.let { uiBehaviorSubject.onNext(it) }
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
        disposable += tvRepository.repositoryGetFavoriteList(tvResult)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it == true) onDeleteFavoriteButtonClicked(tvResult)
                else onFavoriteButtonClicked(tvResult)
            }, {
                it.printStackTrace()
            })
    }

    override fun onFavoriteButtonClicked(item: TVResult?) {
        databaseSubject.onNext(
            Pair(
                { tvRepository.repositoryInputDatabase(item) },
                { _favoriteState.value = true }
            )
        )
        item?.let { uiBehaviorSubject.onNext(it) }
    }

//    fun loadDefault(item: TVResult) {
//        uiBehaviorSubject.onNext(item)
//    }

    fun getLoadBackgroundImage(tvResult: TVResult?): String =
        API.moviePhoto+tvResult?.backdrop_path
}