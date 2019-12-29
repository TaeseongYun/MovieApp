package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseLifeCycleViewModel : ViewModel() {
    val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}