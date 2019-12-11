package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_card_stack_item.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class CardStackRecyclerViewHolder(context: Context?, parent: ViewGroup) :
    BaseRecyclerViewHolder<MovieResult>(
        context, parent, R.layout.movie_card_stack_item
    ) {
    override fun View.onBind(item: MovieResult) {
        if (item.posterPath.isNotEmpty())
            movieBackgroundIMG.loadMovieBackground("https://image.tmdb.org/t/p/w342${item.posterPath}")

        originMovieName.text = item.originalTitle
        koreanMovieName.text = item.title
    }

}