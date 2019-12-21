package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_detail_item_genre.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre

class DetailMovieInformationGenreViewHolder(context: Context?, parent: ViewGroup) :
    BaseRecyclerViewHolder<Genre>(context, parent, R.layout.recycler_detail_item_genre) {
    override fun View.onBind(item: Genre) {
        genre_entry.text = item.name
    }

}