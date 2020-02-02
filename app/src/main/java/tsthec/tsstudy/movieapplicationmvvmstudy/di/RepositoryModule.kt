package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository

val repositoryModule = module {
    single { MovieRepository.getInstance(get(), get()) }
    single { TvRepository.getInstance(get(), get()) }
}