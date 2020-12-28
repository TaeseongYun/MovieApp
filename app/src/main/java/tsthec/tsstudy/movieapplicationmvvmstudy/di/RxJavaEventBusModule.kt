package tsthec.tsstudy.movieapplicationmvvmstudy.di

import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.dsl.module
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls
import tsthec.tsstudy.movieapplicationmvvmstudy.util.BackKeyPressUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.BackKeyPressUtilImpl

val rxJavaModule = module {
    single { RxBusCls }

    factory<BackKeyPressUtil> { (finish: () -> Unit, showToast: (Int, Int) -> Unit, disposable: CompositeDisposable) ->
        BackKeyPressUtilImpl(disposable, finish, showToast)
    }
}