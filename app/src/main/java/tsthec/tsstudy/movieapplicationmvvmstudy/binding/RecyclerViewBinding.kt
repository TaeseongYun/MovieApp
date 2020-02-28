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
    movieAdapter?.notifyDataSetChanged()
}

@BindingAdapter("bindTvList")
fun bindingAdapterTvList(view: RecyclerView, tvList: TVResponse?) {
    val tvAdapter = view.adapter as? MainRecyclerAdapter

    tvList?.let {
        tvAdapter?.addItems(AdapterViewType.DataType.TV, it.results)
    }
    tvAdapter?.notifyDataSetChanged()
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
        searchResultAdapter?.addItems(AdapterViewType.DataType.SEARCH, it)
    }

    searchResultAdapter?.notifyDataSetChanged()
}