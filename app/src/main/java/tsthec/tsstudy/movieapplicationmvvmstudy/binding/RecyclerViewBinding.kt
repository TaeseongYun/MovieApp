package tsthec.tsstudy.movieapplicationmvvmstudy.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.*
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter

@BindingAdapter("bindMovieList")
fun bindingAdapterMovieList(view: RecyclerView, movieList: MovieResponse?) {
    val movieAdapter = view.adapter as? MainRecyclerAdapter


    movieList?.let {
        movieAdapter?.addItems(AdapterViewType.DataType.MOVIE, it.results)
    }
    movieAdapter?.notifyItemRangeChanged(movieAdapter.itemCount, movieAdapter.list.size)
}

@BindingAdapter("bindTvList")
fun bindingAdapterTvList(view: RecyclerView, tvList: TVResponse?) {
    val tvAdapter = view.adapter as? MainRecyclerAdapter

    tvList?.let {
        tvAdapter?.addItems(AdapterViewType.DataType.TV, it.results)
    }
    //notifyItemDataChange 호출 하게되면 전체가 깜빡 거리는 현상이 있기 때문에 기존 아이템 카운트 부터 추가 된 사이즈까지.
    tvAdapter?.notifyItemRangeChanged(tvAdapter.itemCount, tvAdapter.list.size)
}

@BindingAdapter("bindGenreList")
fun bindingAdapterGenreList(view: RecyclerView, genreList: List<Genre>?) {
    val genreAdapter = view.adapter as? MainRecyclerAdapter

    genreList?.let {
        genreAdapter?.addItems(AdapterViewType.DataType.GENRE, it)
    }
    genreAdapter?.notifyDataSetChanged()
}

@BindingAdapter("bindSearchResult")
fun bindingAdapterSearchList(view: RecyclerView, searchResultList: List<SearchResult>?) {
    val searchResultAdapter = view.adapter as? MainRecyclerAdapter

    searchResultList?.let {
        searchResultAdapter?.clearItems()
        searchResultAdapter?.addItems(AdapterViewType.DataType.SEARCH, it)
    }
    if (searchResultList.isNullOrEmpty())
        searchResultAdapter?.clearItems()

    searchResultAdapter?.notifyDataSetChanged()
}

@BindingAdapter("bindingMovieDatabase")
fun bindingAdapterMovieDatabase(view: RecyclerView, movieResultList: List<MovieResult>?) {
    val movieDatabaseAdapter = view.adapter as? MainRecyclerAdapter

    movieResultList?.let {
        movieDatabaseAdapter?.addItems(AdapterViewType.DataType.MOVIE, it)
    }

    movieDatabaseAdapter?.notifyItemRangeChanged(
        movieDatabaseAdapter.itemCount,
        movieResultList?.size ?: -1
    )
}

@BindingAdapter("bindingTVDatabase")
fun bindingAdapterTVDatabase(view: RecyclerView, tvResultList: List<TVResult>?) {
    val movieDatabaseAdapter = view.adapter as? MainRecyclerAdapter

    tvResultList?.let {
        movieDatabaseAdapter?.addItems(AdapterViewType.DataType.TV, it)
    }

    movieDatabaseAdapter?.notifyItemRangeChanged(
        movieDatabaseAdapter.itemCount,
        tvResultList?.size ?: -1
    )
}