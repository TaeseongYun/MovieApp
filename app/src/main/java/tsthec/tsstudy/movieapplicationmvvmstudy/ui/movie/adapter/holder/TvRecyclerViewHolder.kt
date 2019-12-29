package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult

class TvRecyclerViewHolder(onClick: (Int) -> Unit, context: Context?, parent: ViewGroup) :
    BaseRecyclerViewHolder<TVResult>(context, parent, R.layout.recycler_view_detail) {

    init {
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    override fun View.onBind(item: TVResult) {
        if (item.posterPath.isNotEmpty())
            movieBackgroundIMG.loadMovieBackground("${API.moviePhoto}${item.posterPath}")

        originMovieName.text = item.originalName
        koreanMovieName.text = item.name
    }
}