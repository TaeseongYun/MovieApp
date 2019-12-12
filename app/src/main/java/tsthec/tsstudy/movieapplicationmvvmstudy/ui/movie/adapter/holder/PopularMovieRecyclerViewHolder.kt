package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class PopularMovieRecyclerViewHolder(context: Context?, parent: ViewGroup) :
    BaseRecyclerViewHolder<MovieResult>(
        context, parent, R.layout.recycler_view_detail
    ) {
    override fun View.onBind(item: MovieResult) {
        if (item.posterPath.isNotEmpty())
            movieBackgroundIMG.loadMovieBackground("https://image.tmdb.org/t/p/w342${item.posterPath}")

        originMovieName.text = item.originalTitle
        koreanMovieName.text = item.title
    }

}