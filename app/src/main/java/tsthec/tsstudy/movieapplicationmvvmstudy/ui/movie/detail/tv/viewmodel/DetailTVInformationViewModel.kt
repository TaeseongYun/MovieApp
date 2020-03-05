package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.IDetailFavoriteState
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.NavigationProvider
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailTVInformationViewModel(
    private val tvRepository: TvRepository
) : BaseLifeCycleViewModel<TVResult>(), IDetailFavoriteState<TVResult> {

    val tvGenreMutableLiveData = MutableLiveData<List<Genre>>()

    private val _favoriteState = MutableLiveData<Boolean>()

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    //nav detailTV value
    lateinit var detailTVResult: () -> TVResult

    init {
        LogUtil.d("What is value -> ${_favoriteState.value}")

        //load like State
        disposable += tvRepository.getLoadLocalDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (::detailTVResult.isInitialized)
                    _favoriteState.value = it.contains(
                        detailTVResult()
                    )
            }, { it.printStackTrace() })


        //change like state
        disposable += uiBehaviorSubject.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .switchMapSingle {
                if (_favoriteState.value == false || _favoriteState.value == null)
                    tvRepository.repositoryInputDatabase(it)
                else
                    tvRepository.repositoryDeleteDatabase(it)
                tvRepository.repositoryGetFavoriteList(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtil.d("boolean value is ->$it")
                _favoriteState.value = it
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

    fun loadLikeState(tvResult: TVResult) {
        uiBehaviorSubject.onNext(tvResult)
    }

    override fun onFavoriteButtonClicked(item: TVResult?) {
        databaseSubject.onNext(
            Pair(
                { tvRepository.repositoryInputDatabase(item) },
                { _favoriteState.value = true }
            )
        )
    }

    override fun loadFirstLikeState(item: TVResult) {
        disposable += tvRepository.getLoadLocalDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteState.value = it.contains(item)
            }, { it.printStackTrace() })
    }
}