package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter

import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType

abstract class BaseRecyclerAdapter<T> :
    RecyclerView.Adapter<BaseRecyclerViewHolder<*>>(),
    MovieRecyclerModel {

    internal val list = mutableListOf<T>()

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<*>, position: Int) {
        createBindViewHolder(holder, position)
    }

    abstract fun createBindViewHolder(holder: BaseRecyclerViewHolder<*>, position: Int)

    override fun clearItems() {
        list.clear()
    }

    override lateinit var notifiedChangedItem: () -> Unit

    override fun removeAt(position: Int) {
        list.removeAt(position)
    }

    override fun getItemCount(): Int = list.size
}