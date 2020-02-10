package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

abstract class BaseActivity : AppCompatActivity() {
    protected val disposable = CompositeDisposable()

    protected val backKeyPressed = BehaviorSubject.createDefault(0L)

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}