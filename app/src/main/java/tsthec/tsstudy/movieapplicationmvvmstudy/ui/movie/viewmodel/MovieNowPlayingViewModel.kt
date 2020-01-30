package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieNowPlayingViewModel(
    private val movieRepository: MovieRepository
) :
    BaseLifeCycleViewModel() {

    val movieList = MutableLiveData<List<MovieResult>>()

    init {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY, 1)
            .with()
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                isLoading = true
            }
            .doOnSuccess {
                isLoading = false
            }
            .subscribe({ movieResponse ->
                movieList.value = movieResponse.results
            }, {
                Log.e("error", it.message)
            })
    }


    internal var page = 1

    fun loadMorePopularMovie(page: Int) {
        disposable += movieRepository.repositoryPopularMovie(BuildConfig.MOVIE_API_KEY, page)
            .with()
            .doOnSubscribe {
                // :: -> 코틀린 리플렉션? (공부)
                isLoading = true
            }
            .doOnSuccess {
                isLoading = false
            }
            .subscribe({movieResponse: MovieResponse ->
                movieList.value = movieResponse.results
            }, {
                Log.e("error", it.message)
            })
    }
}