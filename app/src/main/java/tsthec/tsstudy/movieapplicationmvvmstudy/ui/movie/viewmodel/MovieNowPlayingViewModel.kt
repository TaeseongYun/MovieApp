package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.tsdev.domain.usecase.MovieSingleUseCase
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

class MovieNowPlayingViewModel(
    private val movieRepository: MovieSingleUseCase<String, MovieResponse>,
    private val tvRepository: TvRepository
) :
    BaseLifeCycleViewModel<MovieResult>() {

    private val _movieList = MutableLiveData<MovieResponse>()

    val movieList: LiveData<MovieResponse>
        get() = _movieList

    private val _tvList = MutableLiveData<TVResponse>()

    val tvList: LiveData<TVResponse>
        get() = _tvList

    val tvListTest = MediatorLiveData<TVResponse>()

    init {
        disposable += movieRepository(BuildConfig.MOVIE_API_KEY, 1)
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

        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY, 1)
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
        disposable += movieRepository(BuildConfig.MOVIE_API_KEY, page)
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
        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY, page)
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
}