package tsthec.tsstudy.movieapplicationmvvmstudy.util.animation

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewAnimationUtils
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun View.requestListener(): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            circleAnimation()
            //왜 true 로 넣으면 넘어가지를 않을까?
            return false
        }
    }
}

fun View.circleAnimation() {
    val centerX = ((this.left + this.right) / 2)
    val centerY = (this.top + this.bottom) / 2

    val finalRadius = this.width.coerceAtLeast(this.height)

    val animation =
        ViewAnimationUtils.createCircularReveal(this, centerX, centerY, 0f, finalRadius.toFloat())

    animation.duration = 300L
    animation.start()
}