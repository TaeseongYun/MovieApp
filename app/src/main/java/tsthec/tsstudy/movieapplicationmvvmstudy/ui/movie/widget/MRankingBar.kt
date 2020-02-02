package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import kotlin.math.roundToInt

class MRankingBar @JvmOverloads constructor(
    context: Context? = null,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.drawable.ic_star_border_white_24dp,
    private val rating: Double
) : SeekBar(context, attrs, defStyleAttr, defStyleRes) {

    init {
        animation = null
        max = 5
        isIndeterminate = false

        val colorStar = ResourcesCompat.getDrawable(resources, R.drawable.ic_star_yellow_24dp, null)
        val borderStar =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_star_border_white_24dp, null)
        val halfStar =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_star_half_yellow_24dp, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        progress = rating.toFloat().roundToInt()

    }
}