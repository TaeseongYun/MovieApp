package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class DetailMovieListByDatabaseRecyclerHolder(
    context: Context?,
    parent: ViewGroup,
    private val iShowDetailMovie: IShowMyDataBase?
) :
    BaseRecyclerViewHolder<MovieResult>(context, parent, R.layout.recycler_view_detail) {

    interface IShowMyDataBase {
        fun onClick(position: Int)
    }

    init {
        containerView.setOnClickListener {
            iShowDetailMovie?.onClick(adapterPosition)
        }
    }
}