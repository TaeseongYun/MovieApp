package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsdev.data.source.TVResponse
import com.tsdev.data.source.TVResult
import com.tsdev.domain.usecase.base.TvSingleUseCase
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.with

class TVViewModel constructor(
    private val tvRepository: TvSingleUseCase<String, TVResponse, TVResult>
) : BaseLifeCycleViewModel<TVResponse>() {

    private val _tvResult = MutableLiveData<List<TVResult>>()

    val input = object : Input {
        override fun setTVResult(items: List<TVResult>) {
            _tvResult.value = items
        }

    }

    val output = object : Output {
        override fun getTvResult(): LiveData<List<TVResult>> = _tvResult
    }

    init {
        disposable += tvRepository(BuildConfig.MOVIE_API_KEY, DEFAULT_PAGE)
            .with()
            .doOnSubscribe {
                _isLoadingMutable.value = true
            }
            .subscribe({
                input.setTVResult(it.results)
                _isLoadingMutable.value = false
            }, {
                it.printStackTrace()
            })
    }

    fun loadMoreTvPage(page: Int) {
        disposable += tvRepository(BuildConfig.MOVIE_API_KEY, page)
            .with()
            .doOnSubscribe {
                _isLoadingMutable.value = true
            }
            .subscribe({
                input.setTVResult(it.results)
                _isLoadingMutable.value = false
            }, {
                it.printStackTrace()
            })
    }

    interface Input {
        fun setTVResult(items: List<TVResult>)
    }

    interface Output {
        fun getTvResult(): LiveData<List<TVResult>>
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}