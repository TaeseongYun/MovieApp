package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.CreditsResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailMovieInformationViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) :
    BaseLifeCycleViewModel() {

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()

    val movieDetailData: LiveData<MovieDetailResponse>
        get() = _movieDetailData

    private val _movieCastData = MutableLiveData<CreditsResponse>()

    val movieCastData: LiveData<CreditsResponse>
        get() = _movieCastData

    fun getResultDetailMovie(movieID: Int) {
        disposable += movieRepository.repositoryDetailMovie(
            movieID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it.genres.forEach { gn ->
                    movieRecyclerModel.addItems(gn)
                }
                _movieDetailData.postValue(it)
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }

    fun getCastingPeopleMovie(movieID: Int) {
        disposable += movieRepository.repositoryCastingMovie(movieID, BuildConfig.MOVIE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _movieCastData.postValue(it)
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }

}