package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter

import android.content.Context
import android.content.res.Resources
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter.BaseRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.DetailMovieInformationGenreViewHolder

class MovieGenreRecyclerAdapter(private val viewType: ViewType, private val context: Context?) :
    BaseRecyclerAdapter<Genre>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<Genre> =
        when (this.viewType) {
            ViewType.GENRE -> DetailMovieInformationGenreViewHolder(context, parent)
            else -> throw Resources.NotFoundException("Here is Detail View")
        }
}