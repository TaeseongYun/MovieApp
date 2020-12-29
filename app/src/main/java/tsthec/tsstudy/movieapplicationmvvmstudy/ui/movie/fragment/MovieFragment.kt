package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tsdev.data.source.MovieResult
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.MovieFragmentBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.PopularMovieRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.DetailMovieActivityArgs
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

class MovieFragment : BaseFragment<MovieFragmentBinding>(R.layout.movie_fragment),
    PopularMovieRecyclerViewHolder.IShowDetailMovie {

    private val movieAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.MOVIE, iShowDetailMovie = this)
    }

    private val movieViewModel by stateViewModel<MovieNowPlayingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieRecyclerView.removeOnScrollListener(addRecyclerViewListener)
    }

    override fun onAttach(context: Context) {
        if (movieRepository.nextPage > 2) {
            movieRepository.nextPage = 1
        }
        super.onAttach(context)
    }

    private val addRecyclerViewListener =
        scrollListener { totalItemCount, visibleItem, firstViewItemIndex ->
            if (movieViewModel.isLoading.value != true && (visibleItem + firstViewItemIndex) >= totalItemCount - 3)
                movieViewModel.loadMorePopularMovie(++movieRepository.nextPage)
        }

    private fun viewModelInit() {
        movieRecyclerView.run {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this.context, 2)
            addOnScrollListener(addRecyclerViewListener)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        this.context?.let { Glide.get(it).clearMemory() }
    }

    override fun onClick(position: Int) {
        findNavController().navigate(
            MovieFragmentDirections.actionMovieFragmentToDetailMovieActivity(
                movieAdapter.getItem(
                    position
                ) as MovieResult
            )
        )
    }
}