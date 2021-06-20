package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.tsdev.data.source.Genre
import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResult
import com.tsdev.domain.usecase.base.MovieCompletableUseCase
import com.tsdev.domain.usecase.base.MovieSingleUseCase
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class DetailMovieInformationViewModel(
    private val handle: SavedStateHandle,
    getLocalMovieListUseCase: MovieSingleUseCase<Unit, List<MovieResult>>,
    private val getLocalMovieUseCase: MovieSingleUseCase<MovieResult?, Boolean>,
    private val getDetailMovieUseCase: MovieSingleUseCase<Int, MovieDetailResponse>,
    private val postMovieLocalInsertUseCase: MovieCompletableUseCase<MovieResult>,
    private val deleteMovieLocalUseCase: MovieCompletableUseCase<MovieResult>,
    private val rxEventBusDataSubject: RxBusCls
) : BaseLifeCycleViewModel<MovieResult>() {

    val genreLiveData = MutableLiveData<List<Genre>>()

    var savedMovieResultID: Int? = handle[DETAIL_MOVIE_KEY]
        set(value) {
            handle[DETAIL_MOVIE_KEY] = value
            field = value
        }

    private val _favoriteState = MutableLiveData<Boolean>()

    lateinit var detailMovieResult: () -> MovieResult

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    init {
        disposable += getLocalMovieListUseCase(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (::detailMovieResult.isInitialized)
                    _favoriteState.value =
                        it.contains(detailMovieResult())
            }, { it.printStackTrace() })

        disposable += uiBehaviorSubject
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .switchMapSingle { movieResult ->
                getLocalMovieUseCase(movieResult)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { likeState: Boolean ->
                if (::detailMovieResult.isInitialized)
                    if (likeState) {
                        rxEventBusDataSubject.publish(
                            Pair(
                                { deleteMovieLocalUseCase(detailMovieResult()) },
                                { _favoriteState.value = false }
                            )
                        )
                    } else {
                        rxEventBusDataSubject.publish(
                            Pair({ postMovieLocalInsertUseCase(detailMovieResult()) },
                                { _favoriteState.value = true }
                            )
                        )
                    }
            }
            .subscribe {
                _isLoadingMutable.value = true
            }
    }


    fun getResultDetailMovie(movieID: Int) {
        savedMovieResultID = movieID
        disposable += getDetailMovieUseCase(movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //동작이 끝났을 때 호출.
            .doAfterTerminate {
                _isLoadingMutable.value = true
            }
//            .onErrorReturn {
//                it.printStackTrace()
//                MovieDetailResponse()
//                MovieDetailResponse(false, ERROR_RESULT.first,ERROR_RESULT.second, emptyList(), ERROR_RESULT.first, )
//            }
            .subscribe { detailResponse: MovieDetailResponse ->
                genreLiveData.value = detailResponse.genres
            }
    }

    private fun setGenre(movieResult: MovieDetailResponse) {
        genreLiveData.value = movieResult.genres
    }

    fun changeLikeState(movieResult: MovieResult) {
        Log.e("ViewModel", "Called")
        uiBehaviorSubject.onNext(movieResult)
    }

    companion object {
        private const val DETAIL_MOVIE_KEY = "detailMovie"
    }
}