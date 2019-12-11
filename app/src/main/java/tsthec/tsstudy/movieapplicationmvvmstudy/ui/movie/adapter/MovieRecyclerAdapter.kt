package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter

import android.content.Context
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter.BaseRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.CardStackRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.MovieRecyclerHolder

class MovieRecyclerAdapter(private val viewType: ViewType, val context: Context?) :
    BaseRecyclerAdapter<MovieResult>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<MovieResult> =
        when (this.viewType) {
            ViewType.NOWPLAYING -> MovieRecyclerHolder(onClick, context, parent)
            ViewType.CARDVIEW -> CardStackRecyclerViewHolder(context, parent)
        }
}