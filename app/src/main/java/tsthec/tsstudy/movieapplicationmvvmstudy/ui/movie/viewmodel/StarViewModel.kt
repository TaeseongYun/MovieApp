package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.data.source.repository.TvRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class StarViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) : BaseLifeCycleViewModel<Any>() {

    private val _databaseMovieList = MutableLiveData<List<MovieResult>>()

    val movieList: LiveData<List<MovieResult>>
        get() = _databaseMovieList

    private val _isMovie = MutableLiveData<Boolean>()

    val isMovie: LiveData<Boolean>
        get() = _isMovie

    private val _databaseTvList = MutableLiveData<List<TVResult>>()

    val databaseTvList: LiveData<List<TVResult>>
        get() = _databaseTvList

    fun loadMovieDataFromDatabase() {
        disposable += movieRepository.repositoryGetListByDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _databaseMovieList.value = it
                _databaseTvList.value = null
                _isMovie.value = true
            }, {
                it.printStackTrace()
            })
    }

    fun loadTvDataFromDatabase() {
        disposable += tvRepository.getLoadLocalDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _databaseMovieList.value = null
                _databaseTvList.value = it
                _isMovie.value = false
            }, {
                it.printStackTrace()
            })
    }
}