package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.tsdev.data.source.*
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import com.tsdev.domain.usecase.base.TvSingleUseCase
import com.tsdev.domain.usecase.movie.params.PopularMovieParams
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

class MovieNowPlayingViewModel(
    private val getMoviePopularListUseCase: MovieSingleUseCase<PopularMovieParams, MovieResponse>,
    private val tvRepository: TvSingleUseCase<String, TVResponse, TVResult>
) :
    BaseLifeCycleViewModel<MovieResult>() {

    private val _movieList = MutableLiveData<MovieResponse>()

    val movieList: LiveData<MovieResponse>
        get() = _movieList

    private val _tvList = MutableLiveData<TVResponse>()

    val tvList: LiveData<TVResponse>
        get() = _tvList

    init {
        disposable += getMoviePopularListUseCase(PopularMovieParams(DEFAULT_PAGE))
            .with()
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                _isLoadingMutable.value = true
            }
            .doAfterTerminate { _isLoadingMutable.value = false }
            .subscribe({ _movieList.value = it }, {
                Log.e("error", it.message.toString())
                it.printStackTrace()
            })

        disposable += tvRepository(BuildConfig.MOVIE_API_KEY, 1)
            .with()
            .doOnSubscribe {
                _isLoadingMutable.value = true
            }
            .subscribe({
                _tvList.value = it
                _isLoadingMutable.value = false
            }, {
                it.printStackTrace()
            })
    }

    fun loadMorePopularMovie(page: Int) {
        disposable += getMoviePopularListUseCase(PopularMovieParams(page))
            .with()
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                _isLoadingMutable.value = true
            }
            .subscribe({
                _movieList.value = it

                _isLoadingMutable.value = false
            }, {
                Log.e("error", it.message.toString())
            })
    }

    fun loadMoreTvPage(page: Int) {
        disposable += tvRepository(BuildConfig.MOVIE_API_KEY, page)
            .with()
            .doOnSubscribe {
                _isLoadingMutable.value = true
            }
            .subscribe({
                _tvList.value = it
                _isLoadingMutable.value = false
            }, {
                it.printStackTrace()
            })
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}