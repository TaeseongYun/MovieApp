package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_movie.toolbar
import kotlinx.android.synthetic.main.activity_detail_tv.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailTvBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MovieGenreRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel.DetailTVInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.inject

class DetailTVActivity : BaseActivity() {

    private val binding by binding<ActivityDetailTvBinding>(R.layout.activity_detail_tv)

    private val genreRecyclerViewAdapter: MovieGenreRecyclerAdapter by lazy {
        MovieGenreRecyclerAdapter(ViewType.GENRE, this)
    }

    private val tvRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI, tvDatabase)
    }

    private val tvDatabase: MovieDatabase by lazy {
        MovieDatabase.getInstance(this)
    }

    private lateinit var tvViewModel: DetailTVInformationViewModel

    override fun viewInit() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }
        viewBinding()

        tvViewModel = DetailTVInformationViewModel::class.java.inject(this) {
            DetailTVInformationViewModel(tvRepository, genreRecyclerViewAdapter)
        }
    }

    private fun viewBinding() {
        with(binding) {
            tvDetailResult = getDetailTV()
            if (!getDetailTV().backdrop_path.isNullOrEmpty())
                glideToolbar.loadMovieBackground("${API.moviePhoto}${getDetailTV().backdrop_path}")
            tv_img.loadMovieBackground("${API.moviePhoto}${getDetailTV().posterPath}")
            rating_tv_TV.text = getDetailTV().voteAverage.toString()
            genre_recyclerView_tv.run {
                adapter = genreRecyclerViewAdapter
                layoutManager = LinearLayoutManager(this.context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
        }
    }

    private fun getDetailTV() =
        intent.getParcelableExtra("tvID") as TVResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()

        // 해당 id 값에 따른 디테일값을 알아야 장르를 recyclerView에 추가시켜 줄 수 있다.
        tvViewModel.getDetailTV(getDetailTV().id)
    }

}
