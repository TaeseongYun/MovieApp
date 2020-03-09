package tsthec.tsstudy.movieapplicationmvvmstudy.di

import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls

val rxJavaModule = module {
    single { RxBusCls }
}