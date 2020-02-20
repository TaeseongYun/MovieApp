package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity

abstract class BaseBindingActivity<out T: Any> : BaseActivity() {
    protected inline fun <reified T : ViewDataBinding> binding(resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

    abstract fun viewInit()

    abstract fun viewBinding()

    abstract fun loadDatabase()

    abstract fun setFavoriteButton(isLike: (Boolean) -> Unit)

    abstract fun getDetail(intent: Intent): T?
}