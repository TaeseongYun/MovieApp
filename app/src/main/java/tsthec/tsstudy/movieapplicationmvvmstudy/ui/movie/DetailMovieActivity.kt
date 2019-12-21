package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.toast
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailMovieBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieGenreRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.DetailMovieInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieDetailViewModelFactory


class DetailMovieActivity : BaseActivity() {

    private val binding by binding<ActivityDetailMovieBinding>(R.layout.activity_detail_movie)


    private val genreRecyclerViewAdapter: MovieGenreRecyclerAdapter by lazy {
        MovieGenreRecyclerAdapter(ViewType.GENRE, this)
    }

    private val detailViewModelFactory: MovieDetailViewModelFactory by lazy {
        MovieDetailViewModelFactory(movieRepository, genreRecyclerViewAdapter)
    }

    private val movieRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI)
    }
    private lateinit var detailViewModel: DetailMovieInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            movieDetailResult = getDetailMovie()
            api = API
            if (!getDetailMovie().backdrop_path.isNullOrEmpty())
                glideToolbar.loadMovieBackground(API.moviePhoto + getDetailMovie().backdrop_path)
            movie_img.loadMovieBackground(API.moviePhoto + getDetailMovie().posterPath)
            rating_tv.text = getDetailMovie().vote_average.toString()
            genre_recyclerView.run {
                adapter = genreRecyclerViewAdapter
                layoutManager = LinearLayoutManager(this.context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
        }

        initView()

        detailViewModel.getResultDetailMovie(getDetailMovie().id)

    }

    private fun getDetailMovie() = intent.getParcelableExtra("movieID") as MovieResult

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }

        detailViewModel = ViewModelProviders.of(
            this,
            detailViewModelFactory
        )[DetailMovieInformationViewModel::class.java]
    }
}
