package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity

abstract class BaseBindingActivity<out T: Any> : BaseActivity() {
    protected inline fun <reified T : ViewDataBinding> binding(resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

    abstract fun viewBinding()

    abstract fun setFavoriteButton(isLike: (Boolean) -> Unit)
}