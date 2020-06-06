package tsthec.tsstudy.movieapplicationmvvmstudy.util.animation

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.material.appbar.AppBarLayout

class CollapsibleToolbar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet,
    defAttr: Int = 0
) : MotionLayout(context, attr, defAttr), AppBarLayout.OnOffsetChangedListener {
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        progress = -verticalOffset / appBarLayout?.totalScrollRange?.toFloat()!!
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (parent as AppBarLayout).addOnOffsetChangedListener(this)
    }
}