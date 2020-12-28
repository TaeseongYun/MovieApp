package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.PROGRAM
import com.tsdev.data.source.SpinnerData
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

    private val _programs = MutableLiveData(SpinnerData(null))

    val program: LiveData<SpinnerData>
        get() = _programs

    val movieList: LiveData<List<MovieResult>>
        get() = _databaseMovieList

    private val _isMovie = MutableLiveData<Boolean>()

    val isMovie: LiveData<Boolean>
        get() = _isMovie

    private val _databaseTvList = MutableLiveData<List<TVResult>>()

    val databaseTvList: LiveData<List<TVResult>>
        get() = _databaseTvList

    val fetchSpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val spinnerData = SpinnerData(parent)
            when (spinnerData.fetchSpinner().program) {
                PROGRAM.TV -> {
                    loadTvDataFromDatabase()
                }
                PROGRAM.MOVIE -> {
                    loadMovieDataFromDatabase()
                }
            }
            _programs.value = spinnerData
        }
    }

    private fun loadMovieDataFromDatabase() {
        disposable += movieRepository.repositoryGetListByDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                _databaseTvList.value = null
                _isMovie.value = true
            }
            .subscribe({
                _databaseMovieList.value = it
            }, {
                it.printStackTrace()
            })
    }

    private fun loadTvDataFromDatabase() {
        disposable += tvRepository.getLoadLocalDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                _databaseMovieList.value = null
                _isMovie.value = false
            }
            .subscribe({
                _databaseTvList.value = it
            }, {
                it.printStackTrace()
            })
    }
}