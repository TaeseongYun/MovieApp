package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailTVInformationViewModel(
    private val tvRepository: MovieRepository
) : BaseLifeCycleViewModel() {

    private val tvMutableMap = mutableMapOf<TVResult, Boolean>()

    val tvGenreMutableLiveData = MutableLiveData<List<Genre>>()

    init {
        disposable += tvRepository.repositoryGetListbyDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tvResultList ->
                    LogUtil.d("$tvResultList")
//                    tvResultList.forEach {
//                        tvMutableMap[it] = true
//                    }
                    LogUtil.d("$tvMutableMap")
                }, { it.printStackTrace() }
            )

    }

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    private fun onFavoriteButtonClicked(tvResult: TVResult) {
//        disposable += tvRepository.repositoryMovieInsertRoomDatabase(tvResult)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                tvMutableMap[tvResult] = true
//                _favoriteState.value = true
//
//            }, { it.printStackTrace() })
    }

    private fun onDeleteFavoriteButtonClicked(tvResult: TVResult) {
//        disposable += tvRepository.repositoryDeleteTvResult(tvResult.id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                tvMutableMap.remove(tvResult)
//                _favoriteState.value = false
//            }, { it.printStackTrace() })
    }

    fun getDetailTV(tvID: Int) {
        disposable += tvRepository.repositoryDetailMovie(
            tvID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                tvGenreMutableLiveData.value = it.genres
            }, {
                it.printStackTrace()
            })
    }

    fun loadLikeState(tvResult: TVResult) {
        when (tvMutableMap[tvResult]) {
            true -> {
                onDeleteFavoriteButtonClicked(tvResult)
            }
            false, null -> {
                onFavoriteButtonClicked(tvResult)
            }
        }
    }

    fun getLoadTVDatabase(paramsId: Int) {
        disposable += tvRepository.repositoryGetDetailMovie(paramsId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
//                _favoriteState.value = tvMutableMap.contains(it)
            }, {
                it.printStackTrace()
            })
    }
}