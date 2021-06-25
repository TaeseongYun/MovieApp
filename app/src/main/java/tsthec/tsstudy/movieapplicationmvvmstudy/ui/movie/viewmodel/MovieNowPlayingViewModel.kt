package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.tsdev.data.source.*
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import com.tsdev.domain.usecase.movie.params.PopularMovieParams
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

class MovieNowPlayingViewModel constructor(
    private val getMoviePopularListUseCase: MovieSingleUseCase<PopularMovieParams, PagedList<MovieResult>>
) : BaseLifeCycleViewModel<MovieResult>() {

    private val _movieList = MutableLiveData<PagedList<MovieResult>>()

    val movieList: LiveData<PagedList<MovieResult>>
        get() = _movieList

    init {
        disposable += getMoviePopularListUseCase.invoke(PopularMovieParams(DEFAULT_PAGE))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                _isLoadingMutable.value = true
            }
            .doAfterTerminate { _isLoadingMutable.value = false }
            .subscribe({
                _movieList.value = it
                Log.e("data", it.toString())
            }, {
                Log.e("error", it.message.toString())
                it.printStackTrace()
            })
    }

    fun loadMorePopularMovie(page: Int) {
        disposable += getMoviePopularListUseCase(PopularMovieParams(page))
            .singleOrError()
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

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}