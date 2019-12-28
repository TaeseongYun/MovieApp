package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.content.res.Resources
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class MovieNowPlayingViewModel
internal constructor(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel,
    private val viewType: ViewType
) :
    BaseLifeCycleViewModel() {

    lateinit var showProgressBar: () -> Unit

    lateinit var hideProgressBar: () -> Unit

    private var page = 0

    var isLoading = false

    private val mutableMovieResult = mutableListOf<MovieResult>()

    private val mutableTvResult = mutableListOf<TVResult>()

    private val _popularMovieListData = MutableLiveData<Pair<List<MovieResult>, MovieResult?>>()

    val popularMovieListData: LiveData<Pair<List<MovieResult>, MovieResult?>>
        get() = _popularMovieListData

    private val _popularTVListData = MutableLiveData<Pair<List<TVResult>, TVResult?>>()

    val popularTvListData: LiveData<Pair<List<TVResult>, TVResult?>>
        get() = _popularTVListData

    init {
        movieRecyclerModel.onClick =
            { position: Int ->
                when(viewType) {
                    ViewType.MOVIE -> {
                        _popularMovieListData.value =
                            Pair(
                                mutableMovieResult,
                                mutableMovieResult[position]
                            )
                    }
                    ViewType.TV -> {
                        _popularTVListData.value =
                            Pair(
                                mutableTvResult,
                                mutableTvResult[position]
                            )
                    }
                    else -> throw Resources.NotFoundException("is not have data class")
                }

            }
    }

    fun loadPopularMovie() {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY, ++page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                if (::showProgressBar.isInitialized) {
                    showProgressBar()
                }
                isLoading = true
            }
            .doOnSuccess {
                isLoading = false

                if (::hideProgressBar.isInitialized) {
                    hideProgressBar()
                }
            }
            .map { mp ->
                mp.results.forEach { movieResult ->
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

    fun loadPopularTV() {
        disposable += movieRepository.repositoryLoadPopularTV(BuildConfig.MOVIE_API_KEY, ++page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if (::showProgressBar.isInitialized)
                    showProgressBar()
                isLoading = true
            }
            .doOnSuccess {
                isLoading = false
                if (::hideProgressBar.isInitialized)
                    hideProgressBar()
            }
            // map -> 데이터를 가공해서 리턴
            .map { tvPopular ->
                tvPopular.results.forEach { tvResult ->
                    mutableTvResult.add(tvResult)

                    movieRecyclerModel.addItems(tvResult)
                }
            }
            .subscribe({
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }
}