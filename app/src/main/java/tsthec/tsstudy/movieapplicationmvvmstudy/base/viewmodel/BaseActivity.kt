package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.koin.android.ext.android.inject
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase

abstract class BaseActivity : AppCompatActivity() {
    protected inline fun <reified T: ViewDataBinding> binding(resId: Int): Lazy<T>
    = lazy { DataBindingUtil.setContentView<T>(this, resId) }

    abstract fun viewInit()

    abstract fun viewBinding()

    protected val movieRepository: MovieRepository by inject()

    protected val movieDatabase: MovieDatabase by inject()
}