package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
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
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil


class DetailTVActivity : BaseBindingActivity<TVResult>() {

    companion object {
        private const val TV = "TV"
        fun getInstance(context: Context?, tv: Any?) {
            context?.startActivity<DetailTVActivity>(TV to tv)
        }
    }

    private val binding by bindingBySetContent<ActivityDetailTvBinding>(R.layout.activity_detail_tv)

    private val genreRecyclerViewAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.GENRE)
    }

    private val tvViewModel by viewModel<DetailTVInformationViewModel>()

    private val detailTvArgs by navArgs<DetailTVActivityArgs>()

    override fun viewINIT() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }
        viewBinding()
    }

    override fun viewBinding() {
        with(binding) {
            tvDetailResult = detailTvArgs.detailTV
            vm = tvViewModel
            api = API
            rating_tv_TV.text = detailTvArgs.detailTV.voteAverage.toString()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewINIT()

        // 해당 id 값에 따른 디테일값을 알아야 장르를 recyclerView에 추가시켜 줄 수 있다.
        tvViewModel.getDetailTV(detailTvArgs.detailTV.id)

        LogUtil.d("getData -> ${detailTvArgs.detailTV}")
        favorite_btn.setOnClickListener {
            tvViewModel.loadLikeState(detailTvArgs.detailTV)
        }

        back_activity.setOnClickListener { finish() }
    }

    override fun setFavoriteButton(isLike: (Boolean) -> Unit) {

    }
}
