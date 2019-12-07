package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult

class MovieRecyclerHolder (context: Context?, parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(context).inflate(R.layout.recycler_view_detail, parent, false)
) {
    private fun View.onBind(item: MovieResult) {
        glideCustomImage.loadMovieBackground("https://image.tmdb.org/t/p/w342${item.posterPath}")
        movie_name.text = item.title
    }

    fun onBind(item: MovieResult) {
        itemView.onBind(item)
    }
}
