package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view_detail.*
import org.jetbrains.anko.startActivity
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


        initState()

        movieViewModel = ViewModelProviders.of(
            this,
            movieViewModelFactory
        )[MovieNowPlayingViewModel::class.java]

        recyclerView.run {
            adapter = movieRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }


        movieViewModel.movieListData.observe(this, Observer {
            it.first.forEach { movieResult ->
                movieRecyclerAdapter.addItems(movieResult) }
//            it.forEach { movieResult ->
//                movieRecyclerAdapter.addItems(movieResult)
//            }
            if(it.second != 0)
                startActivity<DetailMovieActivity>("movieID" to it.second.toString())
            afterGetLiveData()
        })

        movieViewModel.loadMovieList(BuildConfig.MOVIE_API_KEY)


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

    private fun initState() {
        recyclerView?.visibility = View.GONE
        nowPlayingTextView?.visibility = View.GONE
        progress_bar?.visibility = View.VISIBLE
    }

    private fun afterGetLiveData() {
        recyclerView?.visibility = View.VISIBLE
        nowPlayingTextView?.visibility = View.VISIBLE
        progress_bar?.visibility = View.GONE
    }
}
