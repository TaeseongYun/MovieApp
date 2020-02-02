package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieGenreRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.PopularTVRecyclerAdapter

@BindingAdapter("bindMovieList")
fun bindingAdapterMovieList(view: RecyclerView, movieList: List<MovieResult>?) {
    val movieAdapter = view.adapter as? MovieRecyclerAdapter
    movieList?.let {
        it.forEach { movieResult ->
            if (movieAdapter?.list?.contains(movieResult) != true)
                movieAdapter?.addItems(movieResult)
        }
    }
    movieAdapter?.notifyDataSetChanged()
}

@BindingAdapter("bindTvList")
fun bindingAdapterTvList(view: RecyclerView, tvList: List<TVResult>?) {
    val tvAdapter = view.adapter as? PopularTVRecyclerAdapter

    tvList?.let {
        it.forEach { tvResult ->
            if (tvAdapter?.list?.contains(tvResult) != true)
                tvAdapter?.addItems(tvResult)
        }
    }
    tvAdapter?.notifyDataSetChanged()
}

@BindingAdapter("bindGenreList")
fun bindingAdapterGenreList(view: RecyclerView, genreList: List<Genre>?) {
    val genreAdapter = view.adapter as? MovieGenreRecyclerAdapter

    genreList?.let {
        it.forEach { genre ->
            genreAdapter?.addItems(genre)
        }
    }
    genreAdapter?.notifyDataSetChanged()
}