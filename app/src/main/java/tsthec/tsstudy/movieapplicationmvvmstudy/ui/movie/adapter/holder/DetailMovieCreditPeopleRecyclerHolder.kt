package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.movie_credits_people_item.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Cast

class DetailMovieCreditPeopleRecyclerHolder(context: Context?, parent: ViewGroup) :
    BaseRecyclerViewHolder<Cast>(context, parent, R.layout.movie_credits_people_item) {
    override fun View.onBind(item: Cast) {
        if (!item.profilePath.isNullOrEmpty())
            glide_authorImg.loadMovieBackground("https://image.tmdb.org/t/p/w342${item.profilePath}")

        tv_authorName.text = item.name
    }

}