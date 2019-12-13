package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.detail_movie_genre_item.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse

class DetailMovieInformationGenre(context: Context?, parent: ViewGroup) :
    BaseRecyclerViewHolder<Genre>(context, parent, R.layout.detail_movie_genre_item) {
    override fun View.onBind(item: Genre) {
        text_genre.text = item.name
    }
}