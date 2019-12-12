package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository

class OrderByRatingViewModelFactory(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            MovieRepository::class.java
            , MovieRecyclerModel::class.java
        )
            .newInstance(movieRepository, movieRecyclerModel)
    }

}