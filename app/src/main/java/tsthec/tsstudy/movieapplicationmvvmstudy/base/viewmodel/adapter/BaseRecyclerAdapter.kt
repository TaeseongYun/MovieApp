package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter

import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel

@Suppress("UNCHECKED_CAST")
abstract class BaseRecyclerAdapter<T>
    : RecyclerView.Adapter<BaseRecyclerViewHolder<*>>(),
    MovieRecyclerModel {

    internal val list = mutableListOf<T>()

    override fun notifiedChangedItem() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<*>, position: Int) {
        holder.run {
            onBind(list[position])
        }
    }

    override fun addItems(item: Any?) {
        list.add(item as T)
    }

    override fun clearItems() {
        list.clear()
    }

    override fun getItem(position: Int): Any? = list[position]

    override fun getItemCount(): Int = list.size

//    override fun listReserve() = list.reverse()
}