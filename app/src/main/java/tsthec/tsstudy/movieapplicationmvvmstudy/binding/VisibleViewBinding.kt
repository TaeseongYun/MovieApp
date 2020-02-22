package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.bindingVisible(value: Boolean) {
    isVisible = value
}