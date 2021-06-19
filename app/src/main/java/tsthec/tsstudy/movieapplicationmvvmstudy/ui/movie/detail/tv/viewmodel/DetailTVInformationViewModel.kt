package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsdev.data.source.Genre
import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TVResult
import com.tsdev.domain.usecase.base.TvSingleUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailTVInformationViewModel(
    private val tvRepository: TvSingleUseCase<String, TVResponse, TVResult>,
    private val rxBusDatabaseSubject: RxBusCls
) : BaseLifeCycleViewModel<TVResult>() {

    val tvGenreMutableLiveData = MutableLiveData<List<Genre>>()

    private val _favoriteState = MutableLiveData<Boolean>()

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    //nav detailTV value
    lateinit var detailTVResult: () -> TVResult

    init {
        LogUtil.d("What is value -> ${_favoriteState.value}")

        //load like State
        disposable += tvRepository.getLoadDatabases()
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
            .switchMapSingle { tvResult ->
                tvRepository.getFavoriteList(tvResult)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { likeState ->

                //todo tv UseCase
                if (likeState) {

                } else {

                }
//                    rxBusDatabaseSubject.publish(
//                        Pair(
//                            { tvRepository.deleteDatabase(detailTVResult()) },
//                            { _favoriteState.value = false }
//                        )
//                    )
//                else
//                    rxBusDatabaseSubject.publish(
//                        Pair(
//                            { tvRepository.inputDatabase(detailTVResult()) },
//                            { _favoriteState.value = true }
//                        )
//                    )
            }
            .subscribe({
                _isLoadingMutable.value = true
            }, {
                it.printStackTrace()
            })
    }

    fun getDetailTV(tvID: Int?) {
        disposable += tvRepository.detailTV(
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
}