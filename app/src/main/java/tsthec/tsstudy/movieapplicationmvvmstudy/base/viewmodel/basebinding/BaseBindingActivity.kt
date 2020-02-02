package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity

abstract class BaseBindingActivity : BaseActivity() {
    protected inline fun <reified T: ViewDataBinding> binding(resId: Int): Lazy<T>
            = lazy { DataBindingUtil.setContentView<T>(this, resId) }
    abstract fun viewInit()

    abstract fun viewBinding()
}