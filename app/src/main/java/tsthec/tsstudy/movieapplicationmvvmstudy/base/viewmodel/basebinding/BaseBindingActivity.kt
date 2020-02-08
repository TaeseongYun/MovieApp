package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel

abstract class BaseBindingActivity<out T> : BaseActivity() {
    protected inline fun <reified T : ViewDataBinding> binding(resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

    abstract fun viewInit()

    abstract fun viewBinding()

    abstract fun loadDatabase()

    abstract fun setFavoriteButton(isLike:(Boolean) -> Unit)

    abstract fun getDetail(intent: Intent): T?
}