package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.SearchResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MultiSearchRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import java.util.concurrent.TimeUnit

class SearchViewModel(private val searchRepository: MultiSearchRepository) :
    BaseLifeCycleViewModel() {

    val searchResult: MutableLiveData<List<SearchResult>> by lazy {
        MutableLiveData<List<SearchResult>>()
    }

    fun nextSearch(keyword: String) {
        searchKeywordSubject.onNext(keyword)
    }

    lateinit var onShowProgressBar: () -> Unit

    fun loadResult(page: Int) {
        disposable += searchKeywordSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (::onShowProgressBar.isInitialized)
                    onShowProgressBar()
            }
            .debounce(200L, TimeUnit.MILLISECONDS)
            .flatMapSingle {
                searchRepository.multiSearchRepository(it, page)
            }
            .subscribe({
                searchResult.value = it.results
            }, {
                it.printStackTrace()
            })
    }
}