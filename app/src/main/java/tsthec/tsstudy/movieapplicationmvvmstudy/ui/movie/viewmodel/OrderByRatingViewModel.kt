package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieRatingList
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class OrderByRatingViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) : BaseLifeCycleViewModel() {

    private val _orderByMutableLiveData = MutableLiveData<Pair<List<MovieResult>, Int>>()

    val orderByLiveData: LiveData<Pair<List<MovieResult>, Int>>
        get() = _orderByMutableLiveData

    private val orderByBehaviorSubject = BehaviorSubject.create<Pair<MovieRatingList, Int>>()

    lateinit var orderByRatingModel: List<MovieResult>

    init {
        disposable += orderByBehaviorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .filter { it.first.results.isNotEmpty() }
            .map {
                orderByRatingModel = it.first.results
            }
            .subscribe({
                _orderByMutableLiveData.postValue(Pair(orderByRatingModel, 0))
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }


    fun loadOrderByRatingMovies(page: Int) =
        movieRepository.repositoryOrderByRatingMovie(BuildConfig.MOVIE_API_KEY, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ orderByBehaviorSubject.onNext(Pair(it, 0)) }, { it.printStackTrace() })

}