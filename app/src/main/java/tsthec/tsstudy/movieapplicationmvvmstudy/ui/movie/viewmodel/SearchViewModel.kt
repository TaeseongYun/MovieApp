package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.LiveData
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
    BaseLifeCycleViewModel<SearchResult>() {

    private val _searchResult: MutableLiveData<List<SearchResult>> =
        MutableLiveData()

    val searchResult: LiveData<List<SearchResult>>
        get() = _searchResult

    fun nextSearch(keyword: String) {
        searchKeywordSubject.onNext(keyword)
    }

    lateinit var onShowProgressBar: () -> Unit

    fun loadResult(page: Int) {
        disposable += searchKeywordSubject.subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if (::onShowProgressBar.isInitialized)
                    onShowProgressBar()
            }
            .debounce(200L, TimeUnit.MILLISECONDS)
            .filter {
                it.isNotEmpty()
            }
            .switchMapSingle {
                searchRepository.multiSearchRepository(it, page)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchResult.value = it.results
            }, {
                it.printStackTrace()
            })
    }

    override fun onFavoriteButtonClicked(item: SearchResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteFavoriteButtonClicked(item: SearchResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}