package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class DetailMovieListByDatabaseRecyclerHolder(
    onClick: (Int) -> Unit,
    context: Context?,
    parent: ViewGroup
) :
    BaseRecyclerViewHolder<MovieResult>(context, parent, R.layout.recycler_view_detail) {
    init {
        containerView.setOnClickListener {
            onClick(adapterPosition)
        }
    }
}