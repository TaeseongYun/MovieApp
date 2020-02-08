package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class PopularMovieRecyclerViewHolder(
    parent: ViewGroup,
    private val iShowDetailMovie: IShowDetailMovie?
) :
    BaseRecyclerViewHolder<MovieResult>(
        parent.context, parent, R.layout.recycler_view_detail
    ) {

    interface IShowDetailMovie {
        fun onClick(position: Int)
    }

    init {
        containerView.run {
            setOnClickListener {
                iShowDetailMovie?.onClick(adapterPosition)
            }
        }
    }
}