package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.dsl.module
import retrofit2.Retrofit
import tsthec.tsstudy.movieapplicationmvvmstudy.network.MovieInterface
import tsthec.tsstudy.movieapplicationmvvmstudy.network.SearchInterface
import tsthec.tsstudy.movieapplicationmvvmstudy.network.TvInterface

val apiModule = module {
    single { (get() as Retrofit).create(MovieInterface::class.java) }
    single { (get() as Retrofit).create(TvInterface::class.java) }
    single { (get() as Retrofit).create(SearchInterface::class.java) }
}