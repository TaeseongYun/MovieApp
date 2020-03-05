package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.RatingBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil

class MRankingBar : androidx.appcompat.widget.AppCompatRatingBar {

//    private val colorStar =
//        AppCompatResources.getDrawable(context, R.drawable.ic_star_yellow_24dp)
//    private val borderStar =
//        AppCompatResources.getDrawable(context, R.drawable.ic_star_border_white_24dp)
//    private val customStar =
//        AppCompatResources.getDrawable(context, R.drawable.rating_bar_bg)

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        animation = null
        numStars = 5
        isIndeterminate = false
        progress = numStars
        setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating > progress)
                ratingBar.progress = progress
        }
    }

    fun setRating(ratingDoubleValue: Double) {
        progress = (ratingDoubleValue).toInt()
    }
}