package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel.DetailMovieInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel.DetailTVInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.SearchViewModel

val viewModelModule = module {
    viewModel { MovieNowPlayingViewModel(get(), get()) }
    viewModel { DetailMovieInformationViewModel(get()) }
    viewModel { DetailTVInformationViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}