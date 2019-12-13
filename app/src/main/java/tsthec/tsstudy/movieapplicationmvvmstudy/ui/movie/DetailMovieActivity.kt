package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_movie.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieCastingRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieGenreRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.DetailMovieCreditPeopleRecyclerHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.DetailMovieInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieDetailViewModelFactory


class DetailMovieActivity : AppCompatActivity() {

    private val movieRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI)
    }

    private lateinit var detailMovieViewModel: DetailMovieInformationViewModel

    private val detailMovieViewModelFactory by lazy {
        MovieDetailViewModelFactory(movieRepository, detailViewRecyclerAdapter)
    }

    private val detailViewRecyclerAdapter: MovieGenreRecyclerAdapter by lazy {
        MovieGenreRecyclerAdapter(ViewType.GENRE, this)
    }

    private val detailCastRecyclerAdapter: MovieCastingRecyclerAdapter by lazy {
        MovieCastingRecyclerAdapter(this, ViewType.CAST)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        initState()

        detailMovieViewModel.movieDetailData.observe(this, Observer {
            it.genres.forEach { genre ->
                detailViewRecyclerAdapter.addItems(genre)
            }
            detailMovieImgBlor
                .loadMovieBlurImg("https://image.tmdb.org/t/p/w342${it.backdrop_path}")
            detailMovieImg
                .loadMovieBackground("https://image.tmdb.org/t/p/w342${it.posterPath}")

            detailMovieName.text = it.title
            detailMovieRating.text = it.voteAverage.toString()
            tv_movieStory.text = it.overview
            resultState()

        })

        detailMovieViewModel.movieCastData.observe(this, Observer {
            it.cast.forEach { cast ->
                detailCastRecyclerAdapter.addItems(cast)
            }
        })

        detailMovieViewModel.getResultDetailMovie(
            Integer.parseInt(intent.getStringExtra("movieID"))
        )

        detailMovieViewModel.getCastingPeopleMovie(
            Integer.parseInt(intent.getStringExtra("movieID"))
        )

        detailMovieGenreRecyclerView.run {
            adapter = detailViewRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        castingRecyclerView.run {
            adapter = detailCastRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }

    private fun initState() {
        detailMovieGenreRecyclerView?.visibility = View.GONE
        castingRecyclerView?.visibility = View.GONE
        detailMovieViewModel = ViewModelProviders.of(
            this,
            detailMovieViewModelFactory
        )[DetailMovieInformationViewModel::class.java]
    }

    private fun resultState() {
        castingRecyclerView?.visibility = View.VISIBLE
        detailMovieGenreRecyclerView?.visibility = View.VISIBLE
    }
}
