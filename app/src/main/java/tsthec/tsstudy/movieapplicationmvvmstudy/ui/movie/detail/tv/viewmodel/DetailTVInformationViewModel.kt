package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

class DetailTVInformationViewModel(
    private val tvRepository: MovieRepository,
    private val recyclerModel: MovieRecyclerModel
) : BaseLifeCycleViewModel() {

    fun getDetailTV(tvID: Int) {
        disposable += tvRepository.repositoryDetailMovie(
            tvID,
            apiKey = BuildConfig.MOVIE_API_KEY
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.genres.forEach { genre ->
                    recyclerModel.addItems(genre)
                }
            }
            .subscribe({
                recyclerModel.notifiedChangedItem()
            }, {
                it.printStackTrace()
            })
    }
}