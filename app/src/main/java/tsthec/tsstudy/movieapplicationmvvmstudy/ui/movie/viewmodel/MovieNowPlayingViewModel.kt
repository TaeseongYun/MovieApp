package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MoviePopular
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieRatingList
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class MovieNowPlayingViewModel internal constructor(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) :
    BaseLifeCycleViewModel() {


    var page = 1
    var isLoading = false




    private lateinit var movieResponse: List<MovieResult>

    private lateinit var orderByRatingModel: List<MovieResult>

    private lateinit var popularMovieModel: List<MovieResult>

    private val _movieListData = MutableLiveData<Pair<List<MovieResult>, Int>>()

    val movieListData: LiveData<Pair<List<MovieResult>, Int>>
        get() = _movieListData

    private val _orderByMutableLiveData = MutableLiveData<Pair<List<MovieResult>, Int>>()

    val orderByLiveData: LiveData<Pair<List<MovieResult>, Int>>
        get() = _orderByMutableLiveData

    private val _popularMovieListData = MutableLiveData<Pair<List<MovieResult>, Int>>()

    val popularMovieListData: LiveData<Pair<List<MovieResult>, Int>>
        get() = _popularMovieListData

    init {
        movieRecyclerModel.onClick =
            { position: Int ->
                _movieListData.postValue(Pair(movieResponse, movieResponse[position].id))
            }
    }

    fun loadMovieList(apiKey: String, language: String = "ko-KR") {
        disposable += movieRepository.repositoryMovieList(
            apiKey,
            language,
            page
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                movieResponse = it.results
            }
            .subscribe({
                _movieListData.postValue(Pair(movieResponse, 0))
            }, {
                Log.e("error", it.message)
            })
    }

    fun loadOrderByRatingMovies(page: Int) {
        disposable += movieRepository.repositoryOrderByRatingMovie(BuildConfig.MOVIE_API_KEY, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { orderByRatingModel = it.results }
            .subscribe({
                _orderByMutableLiveData.postValue(Pair(orderByRatingModel, 0))
//                movieRecyclerModel.notifiedChangedItem()
            }, { it.printStackTrace() })
    }

    fun loadPopularMovie() {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                popularMovieModel = it.results
            }
            .subscribe({
                _popularMovieListData.value = Pair(popularMovieModel, 0)
                movieRecyclerModel.notifiedChangedItem()
            }, {
                Log.e("error", it.message)
            })
    }
}