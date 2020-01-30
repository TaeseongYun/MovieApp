package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view_detail.view.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult

class TvRecyclerViewHolder(context: Context?, parent: ViewGroup, iShowDetailTv: IShowDetailTv?) :
    BaseRecyclerViewHolder<TVResult>(context, parent, R.layout.tv_detail_recycler_view_item) {

    interface IShowDetailTv {
        fun onClick(position: Int)
    }

    init {
        containerView.setOnClickListener {
            iShowDetailTv?.onClick(adapterPosition)
        }
    }
}