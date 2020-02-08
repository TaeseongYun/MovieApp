package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.subjects.BehaviorSubject
import jp.wasabeef.glide.transformations.CropSquareTransformation
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.util.GlideCustomImage

@BindingAdapter("bindPosterImage")
fun bindingPosterImage(view: GlideCustomImage, posterPath: String?) {
    Glide.with(view)
        .load(API.moviePhoto + posterPath)
        .error(R.drawable.ic_bubble_chart_white_24dp)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_bubble_chart_white_24dp))
        .override(200, 200)// out of memory 방지
        .apply(RequestOptions.bitmapTransform(CropSquareTransformation()))
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        //.apply(RequestOptions.skipMemoryCacheOf(true))// cache사용 이라고 하는 코드
        .diskCacheStrategy(DiskCacheStrategy.ALL)// 디스크 캐시 사용
//        .transition(DrawableTransitionOptions.withCrossFade(1000))
        .into(view)
}
//

interface IActivityFinish {
    fun activityFinish()
}

@BindingAdapter("clickFinishBtn")
fun bindingClickFinish(view: ImageView, activityFin: IActivityFinish) {
    view.setOnClickListener { activityFin.activityFinish() }
}

interface IFavoriteClick {
    fun favoriteButtonEvent(
        favoriteBehaviorSubject: BehaviorSubject<Pair<() -> Unit, () -> Unit>>,
        movieResultData: MovieResult
    )
}

@BindingAdapter("favoriteClick", "favoriteBehavior", "movieResultXmlData")
fun bindingClickFavoriteButton(
    view: ImageView,
    favoriteButtonListener: IFavoriteClick,
    favoriteBehavior: BehaviorSubject<Pair<() -> Unit, () -> Unit>>,
    movieResultData: MovieResult
) {
    view.setOnClickListener {
        favoriteButtonListener.favoriteButtonEvent(
            favoriteBehavior,
            movieResultData
        )
    }
}