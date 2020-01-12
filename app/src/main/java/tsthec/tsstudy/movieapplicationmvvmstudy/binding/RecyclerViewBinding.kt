package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropSquareTransformation
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.GlideCustomImage
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

@BindingAdapter("bindMovieList")
fun bindingAdapterMovieList(view: RecyclerView, movieList: List<MovieResult>) {
    val movieAdapter = view.adapter as? MovieRecyclerAdapter

    movieList.forEach {
        movieAdapter?.addItems(it)
        Log.d("BidingData", it.toString())
    }
    movieAdapter?.notifyDataSetChanged()

}

@BindingAdapter("bindPosterImage")
fun bindingPosterImage(view: GlideCustomImage, posterPath: String) {
    Glide.with(view)
        .load(API.moviePhoto + posterPath)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_bubble_chart_white_24dp))
        .apply(RequestOptions.bitmapTransform(CropSquareTransformation()))
        .transition(DrawableTransitionOptions.withCrossFade(1000))
        .into(view)
}