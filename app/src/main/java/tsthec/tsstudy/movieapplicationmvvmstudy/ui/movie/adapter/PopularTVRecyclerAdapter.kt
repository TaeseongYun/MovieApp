package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter

import android.content.Context
import android.content.res.Resources
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter.BaseRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.TvRecyclerViewHolder

class PopularTVRecyclerAdapter(private val viewType: ViewType, val context: Context?) :
    BaseRecyclerAdapter<TVResult>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<*> =
        when (this.viewType) {
            ViewType.TV -> TvRecyclerViewHolder(onClick, context, parent)
            else -> throw Resources.NotFoundException("is not TV holder")
        }
}