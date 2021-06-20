package tsthec.tsstudy.movieapplicationmvvmstudy.di

import androidx.lifecycle.SavedStateHandle
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel.DetailMovieInformationViewModel
//import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel.DetailMovieInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel.DetailTVInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
//import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.SearchViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.StarViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.TVViewModel

val viewModelModule = module {
    viewModel { MovieNowPlayingViewModel(get(named("getPopularMovie"))) }

    viewModel { TVViewModel(get()) }

    viewModel { (handle: SavedStateHandle) ->
        DetailMovieInformationViewModel(
            handle,
            get(named("getLocalMovies")),
            get(named("getLocalMovie")),
            get(named("getDetailMovie")),
            get(named("postLocalUseCase")),
            get(named("deleteLocalUseCase")),
            get()
        )
    }
    viewModel { DetailTVInformationViewModel(get(), get()) }
//    viewModel { SearchViewModel(get()) }
    viewModel { StarViewModel(get(), get()) }
}