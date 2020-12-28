package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.tsdev.data.source.Genre
import com.tsdev.data.source.MovieDetailResponse
import com.tsdev.data.source.MovieResult
import com.tsdev.domain.usecase.MovieSingleUseCase
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.constant.Const.ERROR_RESULT


class DetailMovieInformationViewModel(
    private val handle: SavedStateHandle,
    private val movieRepository: MovieSingleUseCase<String, MovieDetailResponse, MovieResult>,
    private val rxEventBusDataSubject: RxBusCls
) :
    BaseLifeCycleViewModel<MovieResult>() {

    val genreLiveData = MutableLiveData<List<Genre>>()

    private val DETAIL_MOVIE_KEY = "detailMovie"

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
        disposable += movieRepository.getMovieDatabase()
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
                movieRepository.getMovieDatabaseItem(movieResult)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { likeState: Boolean ->
                if (::detailMovieResult.isInitialized)
                    if (likeState) {
                        rxEventBusDataSubject.publish(
                            Pair(
                                {
                                    movieRepository.getMovieDeleteDatabase(
                                        detailMovieResult()
                                    )
                                },
                                { _favoriteState.value = false }
                            )
                        )
                    } else {
                        rxEventBusDataSubject.publish(
                            Pair({
                                movieRepository.insertMovieDatabase(
                                    detailMovieResult()
                                )
                            }, {
                                _favoriteState.value = true
                            })
                        )
                    }
            }
            .subscribe {
                _isLoadingMutable.value = true
            }
    }


    fun getResultDetailMovie(movieID: Int?) {
        savedMovieResultID = movieID
        disposable += movieRepository.getDetailMovie(
            movieID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).subscribeOn(Schedulers.io())
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
}