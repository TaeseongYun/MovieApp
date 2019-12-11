package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.MovieRecyclerModel
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

@Suppress("UNCHECKED_CAST")
abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder<MovieResult>>(),
    MovieRecyclerModel {

    private val list = mutableListOf<T>()

    override fun notifiedChangedItem() {
        notifyDataSetChanged()
    }

    override lateinit var onClick: (position: Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<MovieResult> =
        createViewHolder(parent, viewType)


    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<MovieResult>, position: Int) {
        holder.onBind(list[position])
    }

    override fun addItems(item: Any?) {
        list.add(item as T)
    }

    override fun clearItems() {
        list.clear()
    }

    override fun getItem(position: Int): Any? = list[position]

    override fun getItemCount(): Int = list.size
}