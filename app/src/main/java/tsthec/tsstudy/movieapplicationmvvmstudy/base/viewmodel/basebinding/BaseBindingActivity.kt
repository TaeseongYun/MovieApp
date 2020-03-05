package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.navArgs
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity

abstract class BaseBindingActivity<out T: Any, VIEW_MODEL: ViewModel> : BaseActivity() {

    protected inline fun <reified T : ViewDataBinding> binding(resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

    abstract fun viewBinding()

    abstract fun setFavoriteButton(isLike: (Boolean) -> Unit)

    abstract fun VIEW_MODEL.initHighOrderFunction()
}