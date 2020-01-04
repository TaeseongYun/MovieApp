package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.BR
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API

@Suppress("UNCHECKED_CAST")
abstract class BaseRecyclerViewHolder<in T : Any?>(
    val context: Context?,
    baseParent: ViewGroup, @LayoutRes layout: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(context).inflate(layout, baseParent, false)
) {
    protected val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

//    abstract fun View.onBind(item: T)

    fun onBind(item: Any?) {
//        itemView.onBind(item as T)
        binding.run {
            setVariable(BR.data, item as T)
            setVariable(BR.API, API)
            executePendingBindings()
        }
    }
}