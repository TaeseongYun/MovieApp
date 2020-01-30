package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class PopularMovieRecyclerViewHolder(
    context: Context?,
    parent: ViewGroup,
    private val iShowDetailMovie: IShowDetailMovie?
) :
    BaseRecyclerViewHolder<MovieResult>(
        context, parent, R.layout.recycler_view_detail
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