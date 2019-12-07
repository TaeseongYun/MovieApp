package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.model.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class MovieNowPlayingViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel<MovieResult>
) :
    BaseLifeCycleViewModel() {


    var page = 0
    private val movieResult = BehaviorSubject.create<MovieResponse>()
    private val _movieListData = MutableLiveData<List<MovieResult>>()

    val movieListData: LiveData<List<MovieResult>>
        get() = _movieListData

    init {
        disposable += movieResult.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                movieRecyclerModel.clearItems()
                _movieListData.postValue(it.results)
                movieRecyclerModel.notifiedChangedItem()
            }, {})
    }

    fun loadMovieList(apiKey: String, language: String = "ko-KR") =
        movieRepository.repositoryMovieList(
            apiKey,
            language,
            ++page
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                movieResult.onNext(it)
            }, {
                Log.e("error", it.message)
            })

}