package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseLifeCycleViewModel : ViewModel() {
    val disposable = CompositeDisposable()

    var isLoading = false

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}