package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder

import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.data.SearchResult

class SearchMultiInformationViewHolder(parent: ViewGroup, private val iSearchItem: ISearchItem?) :
    BaseRecyclerViewHolder<SearchResult>(
        parent.context,
        parent,
        R.layout.detail_search_item
    ) {

    interface ISearchItem {
        fun onClickDetailView(position: Int)
    }

    init {
        containerView.setOnClickListener {
            iSearchItem?.onClickDetailView(adapterPosition)
        }
    }
}