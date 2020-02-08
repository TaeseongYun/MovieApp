package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding.BaseBindingActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailMovieBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel.DetailMovieInformationViewModel


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailMovieActivity : BaseBindingActivity() {

    companion object {
        private const val MOVIE = "movie"
        fun getInstance(context: Context?, movie: Any?) {
            context?.startActivity<DetailMovieActivity>(MOVIE to movie)
        }
    }
    private val binding by binding<ActivityDetailMovieBinding>(R.layout.activity_detail_movie)

    private val genreRecyclerViewAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.GENRE)
    }

    private val detailViewModel by viewModel<DetailMovieInformationViewModel>()

    override fun viewInit() {
        viewBinding()
        loadDatabaes()
        detailViewModel.getResultDetailMovie(getDetailMovie().id)
        back_activity.setOnClickListener { finish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()


        favorite_btn.setOnClickListener {
            detailViewModel.favoriteClick(getDetailMovie())
        }

        showFavoriteState {
            when(it) {
                true -> favorite_btn.setImageResource(R.drawable.ic_favorite_black_24dp)
                false -> favorite_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

    private fun getDetailMovie() =
        intent.getParcelableExtra(MOVIE) as MovieResult

    private fun loadDatabaes() =
        detailViewModel.getLoadDatabase(getDetailMovie().id)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private inline fun showFavoriteState(crossinline isFavorite:( Boolean) -> Unit) {
        detailViewModel.favoriteState.observe(this, Observer {
            isFavorite(it)
        })
    }

    override fun viewBinding() {
        with(binding) {
            movieDetailResult = getDetailMovie()
            api = API
            vm = detailViewModel
            if (!getDetailMovie().backdrop_path.isNullOrEmpty())
                glideToolbar.loadMovieBackground(API.moviePhoto + getDetailMovie().backdrop_path)
            movie_img.loadMovieBackground(API.moviePhoto + getDetailMovie().posterPath)
            genre_recyclerView.run {
                adapter = genreRecyclerViewAdapter
                layoutManager = LinearLayoutManager(this.context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            lifecycleOwner = this@DetailMovieActivity
            executePendingBindings()
        }
    }
}
