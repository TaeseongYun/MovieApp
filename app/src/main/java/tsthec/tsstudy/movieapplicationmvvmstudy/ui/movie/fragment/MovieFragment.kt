package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.MovieFragmentBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.PopularMovieRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.DetailMovieActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

class MovieFragment : BaseFragment(), PopularMovieRecyclerViewHolder.IShowDetailMovie {

    private val movieAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.MOVIE, iShowDetailMovie = this)
    }

    private lateinit var binding: MovieFragmentBinding

    private var count = 0

    private val movieViewModel by stateViewModel<MovieNowPlayingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, R.layout.movie_fragment, container)

        LogUtil.d("Here is onCreateView")
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = movieViewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.d("Here is onViewCreated")

        savedInstanceState?.let {
            count = it.getInt("count")
        }
        fab.setOnClickListener {
            ++count
            LogUtil.d("What is count -> $count")
        }

        LogUtil.d("what is viewmodel hashcode? -> ${movieViewModel.hashCode()}")
        viewModelInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieRecyclerView.removeOnScrollListener(addRecyclerViewListener)
    }

    override fun onAttach(context: Context) {
        if (movieRepository.nextPage > 2)
            movieRepository.nextPage = 1
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
        findNavController().navigate(R.id.detailMovieActivity, bundleOf("detailMovie" to movieAdapter.getItem(position)))
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d("Here is onResume in Fragment")
    }

    override fun onDestroy() {
        LogUtil.d("here is onDestroy")
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d("here is onCreate")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.d("here is onDetach")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        LogUtil.d("here is onSaveInstanceState MovieFragment")
        outState.putInt("count", count)
        super.onSaveInstanceState(outState)
    }
}