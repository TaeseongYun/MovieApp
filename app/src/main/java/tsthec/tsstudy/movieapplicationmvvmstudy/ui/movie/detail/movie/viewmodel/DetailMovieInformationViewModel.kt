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
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign


class DetailMovieInformationViewModel(
    private val handle: SavedStateHandle,
    private val movieRepository: MovieRepository
) :
    BaseLifeCycleViewModel<MovieResult>() {

    val genreLiveData = MutableLiveData<List<Genre>>()

    private val GENRE_KEY = "GENRE"

    //navigation args
    lateinit var detailMovieResult: () -> MovieResult

    var saveGenreState: List<Genre>? = handle[GENRE_KEY]
        set(value) {
            handle[GENRE_KEY] = value
            field = value
        }

//    var getMovieResult

    private val _favoriteState = MutableLiveData<Boolean>()

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    init {
        disposable += movieRepository.repositoryGetListByDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (::detailMovieResult.isInitialized)
                    _favoriteState.value =
                        it.contains(handle.get("detailMovie") ?: detailMovieResult())
            }, { it.printStackTrace() })

        disposable += uiBehaviorSubject
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .switchMapSingle { movieResult ->
                movieRepository.loadCacheDatabaseList(movieResult)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { likeState ->
                if (likeState)
                    databaseSubject.onNext(
                        Pair(
                            { movieRepository.repositoryDeleteDatabase(detailMovieResult()) },
                            { _favoriteState.value = false }
                        )
                    )
                else
                    databaseSubject.onNext(
                        Pair({
                            movieRepository.repositoryMovieInsertRoomDatabase(detailMovieResult())
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
        disposable += movieRepository.repositoryDetailMovie(
            movieID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                genreLiveData.value = it.genres
                saveGenreState = genreLiveData.value
            }, {
                it.printStackTrace()
            })
    }

    fun loadSaveState() {
        genreLiveData.value = saveGenreState
    }

    fun changeLikeState(movieResult: MovieResult) {
        LogUtil.d("This is SavedState value -> ${handle.get<Int>("detailMovie")}")
        uiBehaviorSubject.onNext(movieResult)
    }
}