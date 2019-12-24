package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.CreditsResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailMovieInformationViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) :
    BaseLifeCycleViewModel() {

    init {
        movieRecyclerModel.onFavoriteClick = {

        }
    }

    private val _movieDetailData = MutableLiveData<MovieDetailResponse>()

    val movieDetailData: LiveData<MovieDetailResponse>
        get() = _movieDetailData

    private val _movieCastData = MutableLiveData<CreditsResponse>()

    val movieCastData: LiveData<CreditsResponse>
        get() = _movieCastData

    private val _favoriteState = MutableLiveData<Map<MovieResult, Boolean>>()

    val favoriteState: LiveData<Map<MovieResult, Boolean>>
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
                _movieDetailData.value = it
                movieRecyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }

    fun getCastingPeopleMovie(movieID: Int) {
        disposable += movieRepository.repositoryCastingMovie(movieID, BuildConfig.MOVIE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _movieCastData.postValue(it)
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
                val data = mutableMapOf<MovieResult, Boolean>()
                data[movieResult] = true
                _favoriteState.value = data
            }, {
                it.printStackTrace()
            })
    }

    fun onNotFavoriteButtonClick(movieResult: MovieResult) {
        disposable += movieRepository.repositoryMovieInsertRoomDatabase(movieResult)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //                Log.d("it mr Parameters", "$movieResult")
                val data = mutableMapOf<MovieResult, Boolean>()
                data[movieResult] = false
                _favoriteState.value = data
            }, {
                it.printStackTrace()
            })
    }

    fun favoriteClick() {
//        when (_favoriteState.value) {
//            null -> onFavoriteButtonClick(movieDetail)
//        }
    }
}