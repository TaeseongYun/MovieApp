package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailMovieInformationViewModel(private val movieRepository: MovieRepository) :
    BaseLifeCycleViewModel() {

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()

    val movieDetailData: LiveData<MovieDetailResponse>
        get() = _movieDetailData

    private val behaviorDetailSubject = BehaviorSubject.create<MovieDetailResponse>()

    init {
        disposable += behaviorDetailSubject.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movieDetailData.postValue(it)
            }, {
                it.printStackTrace()
            })
    }

    fun getResultDetailMovie(movieID: Int) =
        movieRepository.repositoryDetailMovie(movieID, apiKey = BuildConfig.MOVIE_API_KEY).
                observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                behaviorDetailSubject.onNext(it)
            }, {
                it.printStackTrace()
            })

}