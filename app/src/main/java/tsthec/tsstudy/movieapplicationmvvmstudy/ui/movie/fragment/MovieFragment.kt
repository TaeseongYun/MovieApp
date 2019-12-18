package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_fragment.*
import org.jetbrains.anko.support.v4.startActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.DetailMovieActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieViewModelFactory

class MovieFragment : Fragment() {

    private val movieAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(ViewType.MOVIE, context)
    }

    private val moviePopularViewModelFactory: MovieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository, movieAdapter)
    }

    private val movieRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI)
    }

    private lateinit var movieViewmodel: MovieNowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewmodel = ViewModelProviders.of(
            this,
            moviePopularViewModelFactory
        )[MovieNowPlayingViewModel::class.java]

        return inflater.inflate(R.layout.movie_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewmodel.loadPopularMovie()

        movieViewmodel.popularMovieListData.observe(this, Observer {
            if(it.second != null)
                startActivity<DetailMovieActivity>("movieID" to it.second)
        })

        movieRecyclerView.run {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this.context, 2)
            addOnScrollListener(addRecyclerViewListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieRecyclerView.removeOnScrollListener(addRecyclerViewListener)
    }

    private val addRecyclerViewListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItemCount = recyclerView.adapter?.itemCount ?: 0

            val visibleItem = recyclerView.childCount

            val firstItemIndex =
                (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            if (!movieViewmodel.isLoading && (visibleItem + firstItemIndex) >= totalItemCount - 5)
                movieViewmodel.loadPopularMovie()
        }
    }
}