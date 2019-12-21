package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.anko.collections.forEachWithIndex
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

    lateinit var showProgressBar: () -> Unit

    lateinit var hideProgressBar: () -> Unit

    private var page = 0

    var isLoading = false

    private val mutableMovieResult = mutableListOf<MovieResult>()

    private val _popularMovieListData = MutableLiveData<Pair<List<MovieResult>, MovieResult?>>()

    val popularMovieListData: LiveData<Pair<List<MovieResult>, MovieResult?>>
        get() = _popularMovieListData

    init {

        movieRecyclerModel.onClick =
            { position: Int ->
                _popularMovieListData.value =
                    Pair(
                        mutableMovieResult,
                        mutableMovieResult[position]
                    )
            }
    }

    fun loadPopularMovie() {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY, ++page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if(::showProgressBar.isInitialized) {
                    showProgressBar()
                }

                isLoading = true

            }
            .doOnSuccess {
                isLoading = false

                if(::hideProgressBar.isInitialized) {
                    hideProgressBar()
                }
            }
            .map { mp ->
                mp.results.forEachWithIndex { i, movieResult ->
                    mutableMovieResult.add(movieResult)

                    movieRecyclerModel.addItems(movieResult)
                }
            }
            .subscribe({
                movieRecyclerModel.notifiedChangedItem()
            }, {
                Log.e("error", it.message)
            })
    }
}