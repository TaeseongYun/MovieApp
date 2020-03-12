package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign


class DetailMovieInformationViewModel(
    private val handle: SavedStateHandle,
    private val movieRepository: MovieRepository,
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
//        LogUtil.d("What is bundle of data -> ${handle.get<MovieResult>(DETAIL_MOVIE_KEY)}")
        disposable += movieRepository.repositoryGetListByDatabase()
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
                movieRepository.loadCacheDatabaseList(movieResult)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { likeState ->
                if (::detailMovieResult.isInitialized)
                    if (likeState)
                        rxEventBusDataSubject.publish(
                            Pair(
                                {
                                    movieRepository.repositoryDeleteDatabase(
                                        detailMovieResult()
//                                handle.get(DETAIL_MOVIE_KEY)
                                    )
                                },
                                { _favoriteState.value = false }
                            )
                        )
                    else
                        rxEventBusDataSubject.publish(
                            Pair({
                                movieRepository.repositoryMovieInsertRoomDatabase(
                                    detailMovieResult()
//                                handle.get(DETAIL_MOVIE_KEY)
                                )
                            }, {
                                _favoriteState.value = true
                            })
                        )
            }
            .subscribe({
                _isLoadingMutable.value = true
            }, {
                it.printStackTrace()
            })
    }


    fun getResultDetailMovie(movieID: Int?) {
        savedMovieResultID = movieID
        disposable += movieRepository.repositoryDetailMovie(
            movieID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                genreLiveData.value = it.genres
            }, {
                it.printStackTrace()
            })
    }

    fun changeLikeState(movieResult: MovieResult) {
        uiBehaviorSubject.onNext(movieResult)
    }
}