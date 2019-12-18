package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_movie.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailMovieBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.DetailMovieInformationViewModel


class DetailMovieActivity : BaseActivity() {

    private val binding by binding<ActivityDetailMovieBinding>(R.layout.activity_detail_movie)
    private val movieRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI)
    }

    private lateinit var detailMovieViewModel: DetailMovieInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        with(binding) {
            movieDetailResult = getDetailMovie()
        }

        tv_test.text = intent.getStringExtra("movieID")

//        glideToolbar.loadMovieBackground()
        Log.d("test", intent.getStringExtra("movieID") ?: "아무것도 없음")
    }

    private fun getDetailMovie() = intent.getParcelableExtra("movieID") as MovieResult
}
