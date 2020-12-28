package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseActivity : AppCompatActivity() {
    protected val disposable = CompositeDisposable()

    protected val backKeyPressed = BehaviorSubject.createDefault(0L)

    inline fun <reified DATA_BIND : ViewDataBinding> bindingBySetContent(@LayoutRes layout: Int): Lazy<DATA_BIND> =
        lazy {
            DataBindingUtil.setContentView<DATA_BIND>(this, layout)
        }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    abstract fun viewINIT()
}