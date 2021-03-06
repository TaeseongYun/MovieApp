package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.BR
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API

@Suppress("UNCHECKED_CAST")
abstract class BaseRecyclerViewHolder<in T : Any?>(
    val context: Context?,
    baseParent: ViewGroup, @LayoutRes layout: Int
) : AndroidExtensionsViewHolder(
    LayoutInflater.from(context).inflate(layout, baseParent, false)
) {
    protected val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    fun onBind(item: Any?) {
        binding.run {
            setVariable(BR.data, item as T)
            setVariable(BR.API, API)
            executePendingBindings()
        }
    }
}