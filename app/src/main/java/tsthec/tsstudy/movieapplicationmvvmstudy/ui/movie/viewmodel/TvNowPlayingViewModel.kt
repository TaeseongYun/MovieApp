package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

class TvNowPlayingViewModel(
    private val tvRepository: TvRepository
) : BaseLifeCycleViewModel() {


    val tvList: MutableLiveData<List<TVResult>> by lazy {
        MutableLiveData<List<TVResult>>()
    }


    init {


        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY)
            .with()
            .doOnSubscribe {
                isLoading = true
            }
            .doOnSuccess {
                isLoading = false
            }
            .subscribe({
                tvList.value = it.results
            }, {
                it.printStackTrace()
            })
    }

    fun loadMoreTvPage() {
        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY)
            .with()
            .doOnSuccess {
                isLoading = true
            }
            .doOnSubscribe {
                isLoading = false
            }
            .subscribe({
                tvList.value = it.results
            }, { it.printStackTrace() })
    }
}