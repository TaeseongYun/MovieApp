package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.CreditsResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign


class DetailMovieInformationViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) :
    BaseLifeCycleViewModel() {

    private val movieData = mutableMapOf<MovieResult, Boolean>()


    init {
        disposable += movieRepository.repositoryGetListbyDatabase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ movieResultList ->
                Log.d("init DatabaseList", "$movieResultList")
                movieResultList.forEach {
                    movieData[it] = true
                }
                Log.d("init Test", "$movieData")
            }, {
                it.printStackTrace()
            })

        Log.d("initTest at out repository", "$movieData")
    }

    private val _movieCastData = MutableLiveData<CreditsResponse>()

    val movieCastData: LiveData<CreditsResponse>
        get() = _movieCastData

    private val _favoriteState = MutableLiveData<Boolean>()

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    fun getResultDetailMovie(movieID: Int) {
        disposable += movieRepository.repositoryDetailMovie(
            movieID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it.genres.forEach { genre ->
                    movieRecyclerModel.addItems(genre)
                }
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }

    private fun onFavoriteButtonClick(movieResult: MovieResult) {
        disposable += movieRepository.repositoryMovieInsertRoomDatabase(movieResult)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //                Log.d("it mr Parameters", "$movieResult")
                movieData[movieResult] = true
                _favoriteState.value = true
            }, {
                it.printStackTrace()
            })
    }

    private fun onNotFavoriteButtonClick(movieResult: MovieResult) {
        disposable += movieRepository.repositoryDeleteDatabase(movieResult.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //                Log.d("it mr Parameters", "$movieResult")
                movieData.remove(movieResult)
                _favoriteState.value = false
            }, {
                it.printStackTrace()
            })
    }

    fun favoriteClick(movieResult: MovieResult) {
        when (movieData[movieResult]) {
            true -> onNotFavoriteButtonClick(movieResult)

            false, null -> onFavoriteButtonClick(movieResult)
        }
    }

    fun getLoadDatabase(parasID: Int) {
        Log.d("MovieResult", "$movieData")
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
}