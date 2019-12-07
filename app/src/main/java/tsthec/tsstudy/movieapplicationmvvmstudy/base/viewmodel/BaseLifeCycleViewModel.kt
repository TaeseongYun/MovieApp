package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseLifeCycleViewModel : ViewModel() {
    val disposable = CompositeDisposable()
//    protected val test = AutoCl

    protected var isLoading = false

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}