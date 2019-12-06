package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.MovieRecyclerHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.model.MovieRecyclerModel

class MovieRecyclerAdapter(private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), MovieRecyclerModel<MovieResult> {

    private val movieList = mutableListOf<MovieResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MovieRecyclerHolder(context, parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieRecyclerHolder).onBind(movieList[position])
    }

    override fun addItems(item: MovieResult) {
        movieList.add(item)
    }

    override fun getItem(position: Int): MovieResult = movieList[position]

    override fun notifiedChangedItem() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movieList.size

    override fun clearItems() {
        movieList.clear()
    }
}