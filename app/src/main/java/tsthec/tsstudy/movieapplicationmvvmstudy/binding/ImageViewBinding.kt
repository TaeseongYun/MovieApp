package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropSquareTransformation
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.util.GlideCustomImage
import tsthec.tsstudy.movieapplicationmvvmstudy.util.animation.requestListener

@BindingAdapter("bindPosterImage")
fun bindingPosterImage(view: GlideCustomImage, posterPath: String?) {
    Glide.with(view)
        .load(API.moviePhoto + posterPath)
        .error(R.drawable.ic_bubble_chart_white_24dp)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_bubble_chart_white_24dp))
        .override(200, 200)// out of memory 방지
        .apply(RequestOptions.bitmapTransform(CropSquareTransformation()))
        //.apply(RequestOptions.skipMemoryCacheOf(true))// cache사용 이라고 하는 코드
        .diskCacheStrategy(DiskCacheStrategy.ALL)// 디스크 캐시 사용
        .transition(DrawableTransitionOptions.withCrossFade(1000))
        .into(view)
}

@BindingAdapter("isLiked")
fun bindingLikeBindingAdapter(view: GlideCustomImage, isLike: Boolean) {
    if (isLike) view.setImageResource(R.drawable.ic_favorite_black_24dp)
    else view.setImageResource(R.drawable.ic_favorite_border_black_24dp)
}

@BindingAdapter("app:apiResource", "app:tvResult")
fun bindingBackgroundImageAdapter(view: GlideCustomImage, api: API, tvResult: TVResult) {
    view.loadMovieBackground("${api.moviePhoto}${tvResult.backdrop_path}")
}

@BindingAdapter("app:apiResource", "app:tvResultPoster")
fun bindingPosterImageAdapter(view: GlideCustomImage, api: API, tvResult: TVResult) {
    view.loadMovieBackground("${api.moviePhoto}${tvResult.posterPath}")
}

@BindingAdapter("app:apiResource", "app:movieResultPoster")
fun bindingPosterMovieBindingAdapter(
    view: GlideCustomImage,
    api: API,
    movieResult: MovieResult
) {
    view.loadMovieBackground("${api.moviePhoto}${movieResult.posterPath}")
}

@BindingAdapter("app:apiResource", "app:movieResult")
fun bindingBackgroundMovieBindingAdapter(
    view: GlideCustomImage,
    api: API,
    movieResult: MovieResult
) {
    view.loadMovieBackground("${api.moviePhoto}${movieResult.backdrop_path}")
}