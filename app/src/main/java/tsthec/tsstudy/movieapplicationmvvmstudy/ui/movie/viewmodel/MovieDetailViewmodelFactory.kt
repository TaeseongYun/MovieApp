package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository

class MovieDetailViewmodelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java)
            .newInstance(movieRepository)
    }
}