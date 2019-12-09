package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.model.MovieRecyclerModel

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    private val movieRepository: MovieRepository,
    private val movieRecyclerModel: MovieRecyclerModel<MovieResult>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(
            MovieRepository::class.java,
            MovieRecyclerModel::class.java
        ).newInstance(movieRepository, movieRecyclerModel)
    }
}