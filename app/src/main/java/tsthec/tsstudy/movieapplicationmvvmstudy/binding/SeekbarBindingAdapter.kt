package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.widget.MRankingBar

@BindingAdapter("app:rating")
fun ratingSeekBarBindingAdapter(view: MRankingBar, rating: Double) {
    view.setRating(rating)
}