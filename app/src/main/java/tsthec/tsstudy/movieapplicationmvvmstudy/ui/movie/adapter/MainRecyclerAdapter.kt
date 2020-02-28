package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter

import android.content.res.Resources
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.adapter.BaseRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder.BaseRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.DetailMovieInformationGenreViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.PopularMovieRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.SearchMultiInformationViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.TvRecyclerViewHolder

class MainRecyclerAdapter(
    private val dataType: AdapterViewType.DataType,
    private val iShowDetailMovie: PopularMovieRecyclerViewHolder.IShowDetailMovie? = null,
    private val iShowDetailTV: TvRecyclerViewHolder.IShowDetailTv? = null,
    private val iSearchItem: SearchMultiInformationViewHolder.ISearchItem? = null
) :
    BaseRecyclerAdapter<AdapterViewType.ViewType?>() {

    companion object {
        private val MOVIE = AdapterViewType.DataType.MOVIE
        private val TV = AdapterViewType.DataType.TV
        private val GENRE = AdapterViewType.DataType.GENRE
        private val SEARCH = AdapterViewType.DataType.SEARCH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<*> =
        when (dataType) {
            MOVIE -> PopularMovieRecyclerViewHolder(parent, iShowDetailMovie)
            TV -> TvRecyclerViewHolder(parent, iShowDetailTV)
            GENRE -> DetailMovieInformationGenreViewHolder(parent)
            SEARCH -> SearchMultiInformationViewHolder(parent, iSearchItem)
            else -> throw Resources.NotFoundException("is not found class viewType -> ")
        }

    override fun addItem(dataType: AdapterViewType.DataType, item: Any?) {
        list.add(AdapterViewType.ViewType(dataType, item))
    }

    override fun createBindViewHolder(holder: BaseRecyclerViewHolder<*>, position: Int) {
        holder.run {
            onBind(list[position]?.item)
        }
    }

    override var notifiedChangedItem: () -> Unit = {
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any? = list[position]?.item

    override fun addItems(dataType: AdapterViewType.DataType, item: List<Any?>?) {
        item?.forEach {
            if (!list.checkList(AdapterViewType.ViewType(dataType, it)))
                addItem(dataType, it)
        }
    }

    //중복 추가 방지
    override fun List<Any?>.checkList(viewType: AdapterViewType.ViewType): Boolean =
        this.contains(viewType)
}