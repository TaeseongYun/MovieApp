package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class StarViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) : BaseLifeCycleViewModel<Any>() {

    val databaseMovieList: MutableLiveData<List<MovieResult>> by lazy {
        MutableLiveData<List<MovieResult>>()
    }

    val isMovie = MutableLiveData<Boolean>(true)

    val databaseTvList: MutableLiveData<TVResponse> by lazy {
        MutableLiveData<TVResponse>()
    }

//    fun loadMovieDataFromDatabase() {
//        disposable += movieRepository.repositoryGetListbyDatabase()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                databaseMovieList.value = it
//            }, {
//                it.printStackTrace()
//            })
//    }
}