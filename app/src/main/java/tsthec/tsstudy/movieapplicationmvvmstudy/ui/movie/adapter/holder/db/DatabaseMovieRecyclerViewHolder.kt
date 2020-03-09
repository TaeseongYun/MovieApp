package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.db

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class DatabaseMovieRecyclerViewHolder(parent: ViewGroup, @LayoutRes layout: Int) :
    BaseRecyclerViewHolder<MovieResult>(parent.context, parent, layout) {
    init {
        containerView.setOnClickListener {

        }
    }
}