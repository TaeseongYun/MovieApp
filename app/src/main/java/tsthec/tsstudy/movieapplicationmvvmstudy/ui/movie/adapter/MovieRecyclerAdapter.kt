package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter

import android.content.Context
import android.content.res.Resources
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter.BaseRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.DetailMovieListByDatabaseRecyclerHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.PopularMovieRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.TvRecyclerViewHolder

class MovieRecyclerAdapter(
    private val viewType: ViewType,
    val context: Context?,
    private val iShowDetailMovie: PopularMovieRecyclerViewHolder.IShowDetailMovie? = null,
    private val iShowMyDatabase: DetailMovieListByDatabaseRecyclerHolder.IShowMyDataBase? = null
) :
    BaseRecyclerAdapter<MovieResult>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<MovieResult> =
        when (this.viewType) {
            ViewType.MOVIE -> PopularMovieRecyclerViewHolder(
                context,
                parent,
                iShowDetailMovie
                )
            ViewType.STAR -> DetailMovieListByDatabaseRecyclerHolder(context, parent, iShowMyDatabase)
            else -> throw Resources.NotFoundException("Is not have holder!")
        }
}