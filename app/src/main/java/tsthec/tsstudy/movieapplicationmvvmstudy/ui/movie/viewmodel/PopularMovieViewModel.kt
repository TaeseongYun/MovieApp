package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MoviePopular
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class PopularMovieViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) : BaseLifeCycleViewModel() {

    private val popularMovieResult = BehaviorSubject.create<Pair<MoviePopular, Int>>()

    private val _popularMovieListData = MutableLiveData<Pair<List<MovieResult>, Int>>()

    val popularMovieListData: LiveData<Pair<List<MovieResult>, Int>>
        get() = _popularMovieListData

    private lateinit var movieResponse: List<MovieResult>

    init {
        disposable += popularMovieResult.observeOn(AndroidSchedulers.mainThread())
            .map {
                it.also {
                    movieResponse = it.first.results
                }
            }
            .subscribe({
                _popularMovieListData.postValue(Pair(movieResponse, 0))
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }

    fun loadPopularMovie() =
        movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY).observeOn(
            AndroidSchedulers.mainThread()
        )
            .subscribeOn(Schedulers.io())
            .subscribe({
                popularMovieResult.onNext(Pair(it, 0))
            }, {
                Log.e("error", it.message)
            })
}