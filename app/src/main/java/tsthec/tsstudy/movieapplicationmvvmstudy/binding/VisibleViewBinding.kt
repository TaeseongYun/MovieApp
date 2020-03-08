package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.bindingVisible(value: Boolean) {
    isVisible = value
}

@BindingAdapter("movieRecyclerVisible")
fun View.bindingMovieVisible(value: Boolean) {
    isVisible = value
}

@BindingAdapter("tvRecyclerVisible")
fun View.bindingTVVisible(value: Boolean) {
    isVisible = !value
}