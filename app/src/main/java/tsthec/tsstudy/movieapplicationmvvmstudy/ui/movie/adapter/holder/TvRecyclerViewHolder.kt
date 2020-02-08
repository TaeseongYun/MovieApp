package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult

class TvRecyclerViewHolder(parent: ViewGroup, iShowDetailTv: IShowDetailTv?) :
    BaseRecyclerViewHolder<TVResult>(parent.context, parent, R.layout.tv_detail_recycler_view_item) {

    interface IShowDetailTv {
        fun onClick(position: Int)
    }

    init {
        containerView.setOnClickListener {
            iShowDetailTv?.onClick(adapterPosition)
        }
    }
}