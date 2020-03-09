package tsthec.tsstudy.movieapplicationmvvmstudy.util

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropSquareTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.util.animation.requestListener

class GlideCustomImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    fun loadMovieBackground(baseUrl: String, @DrawableRes placeholder: Int = R.drawable.ic_bubble_chart_white_24dp) {
        Glide.with(this)
            .load(baseUrl)
            .apply(RequestOptions.placeholderOf(placeholder))
            .apply(RequestOptions.bitmapTransform(CropSquareTransformation()))
            .listener(this.requestListener())
            .into(this)
    }

    fun loadMovieBlurImg(baseUrl: String, @DrawableRes placeholder: Int = R.drawable.ic_bubble_chart_white_24dp) {
        val options = MultiTransformation<Bitmap>(
            BlurTransformation(5, 4),
            RoundedCornersTransformation(120, 0, RoundedCornersTransformation.CornerType.BOTTOM)
        )
        Glide.with(this)
            .load(baseUrl)
            .apply(RequestOptions.bitmapTransform(options))
            .apply(RequestOptions.placeholderOf(placeholder))
            .into(this)
    }
}