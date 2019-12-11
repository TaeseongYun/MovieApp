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
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class MovieNowPlayingViewModel internal constructor(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) :
    BaseLifeCycleViewModel() {


    var page = 1
    var isLoading = false
    private val movieResult = BehaviorSubject.create<Pair<MovieResponse, Int>>()
    private val _movieListData = MutableLiveData<Pair<List<MovieResult>, Int>>()

    private lateinit var movieResponse: List<MovieResult>

    val movieListData: LiveData<Pair<List<MovieResult>, Int>>
        get() = _movieListData

    init {
        disposable += movieResult.observeOn(AndroidSchedulers.mainThread())
            .map {
                it.also {
                    movieResponse = it.first.results
                }
            }
            .subscribe({
                isLoading = true
                _movieListData.postValue(Pair(movieResponse, 0))
                movieRecyclerModel.notifiedChangedItem()
                isLoading = false
            }, {
                it.printStackTrace()
            })

        movieRecyclerModel.onClick = { position: Int ->
            _movieListData.postValue(Pair(movieResponse, movieResponse[position].id))
        }
    }

    fun loadMovieList(apiKey: String, language: String = "ko-KR") =
        movieRepository.repositoryMovieList(
            apiKey,
            language,
            page
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                movieResult.onNext(Pair(it, 0))
            }, {
                Log.e("error", it.message)
            })
}