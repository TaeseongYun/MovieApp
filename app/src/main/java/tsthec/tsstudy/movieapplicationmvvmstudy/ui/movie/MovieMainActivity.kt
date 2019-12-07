package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieViewModelFactory

@Suppress("CAST_NEVER_SUCCEEDS")
class MovieMainActivity : AppCompatActivity() {


    private val movieRepository: MovieRepository
        get() = MovieRepository.getInstance(RetrofitObject.movieAPI)

    private val movieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository, movieRecyclerAdapter)
    }

    private val movieRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(this)
    }

    private lateinit var movieViewModel: MovieNowPlayingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        movieViewModel = ViewModelProviders.of(
            this,
            movieViewModelFactory
        )[MovieNowPlayingViewModel::class.java]

        recyclerView.run {
            adapter = movieRecyclerAdapter
            layoutManager = GridLayoutManager(this.context, 1)
            addOnScrollListener(recyclerViewScrollListener)
        }


        movieViewModel.movieListData.observe(this, Observer {
            it.forEach { movieResult ->
                movieRecyclerAdapter.addItems(movieResult)
            }
        })

        movieViewModel.loadMovieList(BuildConfig.MOVIE_API_KEY)

    }

    override fun onDestroy() {
        recyclerView.removeOnScrollListener(recyclerViewScrollListener)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager).run {
            (menu?.findItem(R.id.app_bar_search)?.actionView as SearchView).run {
                queryHint = getString(R.string.query_string_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
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


    private val recyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val totalChildItemCount = recyclerView.adapter?.itemCount ?: 0
            val firstViewItemIndex =
                (recyclerView as? GridLayoutManager)?.findFirstVisibleItemPosition() ?: 0
            val itemVisibleCount = recyclerView.childCount
            if ((firstViewItemIndex + itemVisibleCount) >= totalChildItemCount - 17) {
                movieViewModel.page += 1
                movieViewModel.loadMovieList(BuildConfig.MOVIE_API_KEY)
            }
            super.onScrolled(recyclerView, dx, dy)
        }
    }
}
