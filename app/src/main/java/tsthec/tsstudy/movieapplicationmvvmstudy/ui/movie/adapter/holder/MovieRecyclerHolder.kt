package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class MovieRecyclerHolder(onClick: (position: Int) -> Unit, context: Context?, parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(context).inflate(R.layout.recycler_view_detail, parent, false)
) {

    init {
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    private fun View.onBind(item: MovieResult) {
        if (!item.posterPath.isNullOrEmpty())
            glideCustomImage.loadMovieBackground("https://image.tmdb.org/t/p/w342${item.posterPath}")
        movie_name.text = item.title
        movie_author.text = item.overview
        glideCustomImage.animation = AnimationUtils.loadAnimation(context, R.anim.recycler_animation)
    }

    fun onBind(item: MovieResult) {
        itemView.onBind(item)
    }
}
