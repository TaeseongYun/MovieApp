package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.startActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.*

@Suppress("CAST_NEVER_SUCCEEDS")
class MovieMainActivity : AppCompatActivity() {
    private val inVisibleList =
        listOf(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE)

    private val visibleList =
        listOf(
            View.VISIBLE,
            View.VISIBLE,
            View.GONE,
            View.VISIBLE,
            View.VISIBLE,
            View.VISIBLE,
            View.VISIBLE
        )


    private val movieRepository: MovieRepository
        get() = MovieRepository.getInstance(RetrofitObject.movieAPI)

    private val movieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository, nowPlayingRecyclerAdapter)
    }

    private val nowPlayingRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(ViewType.NOWPLAYING, this)
    }

    private val nowPopularMovieRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(ViewType.NOW_POPULAR, this)
    }

    private val orderByRatingMovieRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(ViewType.ORDER_BY_RATING, this)
    }

    private lateinit var movieViewModel: MovieNowPlayingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        initState()

        recyclerView.run {
            adapter = nowPlayingRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        nowPopularMovie_recyclerView.run {
            adapter = nowPopularMovieRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        ratingMovieRecyclerView.run {
            adapter = orderByRatingMovieRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        movieViewModel.movieListData.observe(this, Observer {
            it.first.forEach { movieResult ->
                nowPlayingRecyclerAdapter.addItems(movieResult)

            }

            if (it.second != 0)
                startActivity<DetailMovieActivity>("movieID" to it.second.toString())

        })

        movieViewModel.popularMovieListData.observe(this, Observer {
            it.first.forEach { mv ->
                nowPopularMovieRecyclerAdapter.addItems(mv)
            }
            afterGetLiveData()
        })

        movieViewModel.orderByLiveData.observe(this, Observer {
            it.first.forEach { mv ->
                orderByRatingMovieRecyclerAdapter.addItems(mv)
            }

        })

        movieViewModel.loadPopularMovie()

        movieViewModel.loadMovieList(BuildConfig.MOVIE_API_KEY)

        movieViewModel.loadOrderByRatingMovies(1)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager).run {
            (menu?.findItem(R.id.app_bar_search)?.actionView as SearchView).run {
                queryHint = getString(R.string.query_string_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        initState()
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }
        }
        return true
    }

    private fun initState() {
        invisibleFunction(
            listOf(
                recyclerView,
                nowPlayingTextView,
                progress_bar,
                lottieAnimation,
                cardView,
                orderByRatingRecyclerView,
                popularMovieRelativeLayout
            )
        )

        movieViewModel = ViewModelProviders.of(
            this,
            movieViewModelFactory
        )[MovieNowPlayingViewModel::class.java]
    }

    private fun afterGetLiveData() {
        visibleFunction(
            listOf(
                recyclerView,
                nowPlayingTextView,
                progress_bar,
                lottieAnimation,
                cardView,
                orderByRatingRecyclerView,
                popularMovieRelativeLayout
            )
        )
    }

    private fun invisibleFunction(listView: List<View>) {
        listView.forEachWithIndex { i, view ->
            view.visibility = inVisibleList[i]
        }
    }

    private fun visibleFunction(listView: List<View>) {
        listView.forEachWithIndex { index, view ->
            view.visibility = visibleList[index]
        }
    }
}
