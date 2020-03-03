package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val movieRepository: MovieRepository
) :
    BaseLifeCycleViewModel<MovieResult>() {

    private val movieData = mutableMapOf<MovieResult, Boolean>()

    val genreLiveData = MutableLiveData<List<Genre>>()

    var testWord = ""

    init {
        disposable += movieRepository.repositoryGetListbyDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieResultList ->
                Log.d("init DatabaseList", "$movieResultList")
                movieResultList.forEach {
                    movieData[it] = true
                }
                Log.d("init Test", "$movieData")
            }, {
                it.printStackTrace()
            })

        disposable += testKeyword
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                testWord = it
                LogUtil.d("What is testWord $testWord")
            }, {
                it.printStackTrace()
            })
    }

    private val _favoriteState = MutableLiveData<Boolean>()

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    fun getResultDetailMovie(movieID: Int?) {
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

    fun getLoadDatabase(parasID: Int?) {
        disposable += movieRepository.repositoryGetDetailMovie(parasID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DatabaseMovieResult", "$it")
                _favoriteState.value = movieData.containsKey(it)
            }, {
                it.printStackTrace()
            })
    }

    fun loadLikeState(movieResult: MovieResult?) {
        when (movieData[movieResult]) {
            true -> {
                onDeleteFavoriteButtonClicked(movieResult)
            }
            null, false -> {
                onFavoriteButtonClicked(movieResult)
            }
        }
    }

    fun nextWord(word: String) {
        testKeyword.onNext(word)
    }

    override fun onFavoriteButtonClicked(item: MovieResult?) {
        databaseSubject.onNext(
            Pair(
                { movieRepository.repositoryMovieInsertRoomDatabase(item) },
                { _favoriteState.value = true }
            )
        )
    }

    override fun onDeleteFavoriteButtonClicked(item: MovieResult?) {
        databaseSubject.onNext(
            Pair(
                { movieRepository.repositoryDeleteDatabase(item?.id) },
                { _favoriteState.value = false }
            )
        )
    }
}