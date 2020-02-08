package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieNowPlayingViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) :
    BaseLifeCycleViewModel() {

    val movieList = MutableLiveData<MovieResponse>()

    val tvList: MutableLiveData<TVResponse> by lazy {
        MutableLiveData<TVResponse>()
    }

    init {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY, 1)
            .with()
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                _isLoadingMutable.value = true
            }
            .subscribe({
                movieList.value = it
                _isLoadingMutable.value = false
            }, {
                Log.e("error", it.message)
            })

        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY, 1)
            .with()
            .doOnSubscribe {
                _isLoadingMutable.value = true
            }
            .subscribe({
                tvList.value = it
                _isLoadingMutable.value = false
            }, {
                it.printStackTrace()
            })
    }

    fun loadMorePopularMovie(page: Int) {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY, page)
            .with()
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                _isLoadingMutable.value = true
            }
            .subscribe({
                movieList.value = it
                _isLoadingMutable.value = false
            }, {
                Log.e("error", it.message)
            })
    }

    fun loadMoreTvPage(page: Int) {
        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY, page)
            .with()
            .doOnSubscribe {
                _isLoadingMutable.value = true
            }
            .subscribe({
                tvList.value = it
                _isLoadingMutable.value = false
            }, {
                it.printStackTrace()
            })
    }
}