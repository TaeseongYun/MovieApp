package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_tv.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding.BaseBindingActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailTvBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel.DetailTVInformationViewModel


class DetailTVActivity : BaseBindingActivity() {

    companion object {
        private const val TV = "TV"
        fun getInstance(context: Context?, tv: Any?) {
            context?.startActivity<DetailTVActivity>(TV to tv)
        }
    }

    private val binding by binding<ActivityDetailTvBinding>(R.layout.activity_detail_tv)

    private val genreRecyclerViewAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.GENRE)
    }

    private val tvViewModel by viewModel<DetailTVInformationViewModel>()

    override fun viewInit() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }
        viewBinding()

        getLoadTVDatabase()
    }

    override fun viewBinding() {
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
            lifecycleOwner = this@DetailTVActivity
            executePendingBindings()
        }
    }

    private fun getDetailTV() =
        intent.getParcelableExtra(TV) as TVResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()

        // 해당 id 값에 따른 디테일값을 알아야 장르를 recyclerView에 추가시켜 줄 수 있다.
        tvViewModel.getDetailTV(getDetailTV().id)

        favorite_btn.setOnClickListener {
            tvViewModel.loadLikeState(getDetailTV())
        }

        back_activity.setOnClickListener { finish() }
        setFavoriteButton { isLike ->
            when (isLike) {
                true -> favorite_btn.setImageResource(R.drawable.ic_favorite_black_24dp)
                false -> favorite_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

    private inline fun setFavoriteButton(crossinline isClicked: (isLike: Boolean) -> Unit) {
        tvViewModel.favoriteState.observe(this, Observer {
            isClicked(it)
        })
    }

    private fun getLoadTVDatabase() =
        tvViewModel.getLoadTVDatabase(getDetailTV().id)
}
