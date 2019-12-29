package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_fragment.*
import org.jetbrains.anko.support.v4.startActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.DetailMovieActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieViewModelFactory
import tsthec.tsstudy.movieapplicationmvvmstudy.util.inject
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

class MovieFragment : Fragment() {
    private val movieAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(ViewType.MOVIE, context)
    }

    private val moviePopularViewModelFactory: MovieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository, movieAdapter)
    }

    private val movieRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI, movieDatabase)
    }

    private val movieDatabase: MovieDatabase by lazy {
        MovieDatabase.getInstance(this.context!!)
    }

    private lateinit var movieViewModel: MovieNowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        movieViewModel = ViewModelProviders.of(
//            this,
//            moviePopularViewModelFactory
//        )[MovieNowPlayingViewModel::class.java]
        movieViewModel = MovieNowPlayingViewModel::class.java.inject(this) {
            MovieNowPlayingViewModel(movieRepository, movieAdapter, ViewType.MOVIE)
        }
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInit()

        movieViewModel.loadPopularMovie()

        movieViewModel.popularMovieListData.observe(this, Observer {
            if (it.second != null)
                startActivity<DetailMovieActivity>("movieID" to it.second)
        })

        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(this.context, R.anim.layoutanimation)

        movieRecyclerView.run {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this.context, 2)
            addOnScrollListener(addRecyclerViewListener)
            layoutAnimation = controller
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieRecyclerView.removeOnScrollListener(addRecyclerViewListener)
    }

    private val addRecyclerViewListener =
        scrollListener { totalItemCount, visibleItem, firstViewItemIndex ->
            if (!movieViewModel.isLoading && (visibleItem + firstViewItemIndex) >= totalItemCount - 3)
                movieViewModel.loadPopularMovie()
        }

    private fun viewModelInit() {
        movieViewModel.run {
            showProgressBar = {
                loading_group_progress?.visibility = View.VISIBLE
            }

            hideProgressBar = {
                loading_group_progress?.visibility = View.GONE
            }
        }
    }
}