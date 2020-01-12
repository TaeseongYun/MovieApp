package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository

val repositoryModule = module {
    single { MovieRepository.getInstance(get(), get())}
}