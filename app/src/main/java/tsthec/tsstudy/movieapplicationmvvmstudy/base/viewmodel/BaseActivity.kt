package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseActivity : AppCompatActivity() {
    protected val disposable = CompositeDisposable()

    protected val backKeyPressed = BehaviorSubject.createDefault(0L)

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}