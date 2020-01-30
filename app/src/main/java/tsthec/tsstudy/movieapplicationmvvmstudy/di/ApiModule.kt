package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.dsl.module
import retrofit2.Retrofit
import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface
import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface

val apiModule = module {
    single { get<Retrofit>().create(MovieInterface::class.java) }
    single { get<Retrofit>().create(TvInterface::class.java) }
}