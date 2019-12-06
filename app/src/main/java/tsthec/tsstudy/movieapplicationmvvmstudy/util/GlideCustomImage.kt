package tsthec.tsstudy.movieapplicationmvvmstudy.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tsthec.tsstudy.movieapplicationmvvmstudy.R

class GlideCustomImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
) : ImageView(context, attrs, defStyleAttr) {
    fun loadMovieBackground(baseUrl: String, @DrawableRes placeholder: Int = R.drawable.ic_bubble_chart_white_24dp) {
        Glide.with(this)
            .load(baseUrl)
            .apply(RequestOptions.placeholderOf(placeholder).centerCrop())
            .into(this)
    }
}