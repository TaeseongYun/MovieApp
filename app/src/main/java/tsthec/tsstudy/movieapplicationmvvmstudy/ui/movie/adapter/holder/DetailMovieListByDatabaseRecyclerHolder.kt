package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.app.ActivityOptions
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import org.jetbrains.anko.activityManager
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class DetailMovieListByDatabaseRecyclerHolder(
    onClick: (Int) -> Unit,
    context: Context?,
    parent: ViewGroup
) :
    BaseRecyclerViewHolder<MovieResult>(context, parent, R.layout.recycler_view_detail) {
    init {
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    override fun View.onBind(item: MovieResult) {
        if (item.posterPath.isNotEmpty())
            movieBackgroundIMG.loadMovieBackground("${API.moviePhoto}${item.posterPath}")
        originMovieName.text = item.originalTitle
        koreanMovieName.text = item.title
    }
}