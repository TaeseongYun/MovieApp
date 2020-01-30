package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.MovieFragmentBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.DetailMovieActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.PopularMovieRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.inject
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

class MovieFragment : BaseFragment(), PopularMovieRecyclerViewHolder.IShowDetailMovie {

    private val movieAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(ViewType.MOVIE, context, iShowDetailMovie = this)
    }

    private lateinit var binding: MovieFragmentBinding

    private lateinit var movieViewModel: MovieNowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, R.layout.movie_fragment, container)

        movieViewModel = MovieNowPlayingViewModel::class.java.inject(this) {
            MovieNowPlayingViewModel(movieRepository
//                ViewType.MOVIE
            )
        }


        binding.lifecycleOwner = this
        binding.vm = movieViewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInit()
    }
//    private fun startActivityObserve() {
//        movieViewModel.popularMovieListData.observe(this, Observer {
//            if (it.second != null)
//                startActivity<DetailMovieActivity>("movieID" to it.second)
//        })
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieRecyclerView.removeOnScrollListener(addRecyclerViewListener)
        movieAdapter.clearItems()
    }

    private val addRecyclerViewListener =
        scrollListener { totalItemCount, visibleItem, firstViewItemIndex ->
            if (!movieViewModel.isLoading && (visibleItem + firstViewItemIndex) >= totalItemCount - 3)
                movieViewModel.loadMorePopularMovie(2)
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
        DetailMovieActivity.getInstance(context, movieAdapter.getItem(position))
    }

}