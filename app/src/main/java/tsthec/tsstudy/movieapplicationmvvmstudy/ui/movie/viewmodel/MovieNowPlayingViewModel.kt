package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BehaviorSubjectViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.model.MovieRecyclerModel

class MovieNowPlayingViewModel(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel<MovieResult>
) :
    BehaviorSubjectViewModel() {

    fun loadMovieList(apiKey: String, language: String = "ko-KR", page: Int) =
        movieRepository.repositoryMovieList(
            apiKey,
            language,
            page
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it.results.forEach { movieResult ->
                    movieRecyclerModel.addItems(
                        movieResult
                    )
                    movieRecyclerModel.notifiedChangedItem()
                }
            }, {
                Log.e("error", it.message)
            })
}