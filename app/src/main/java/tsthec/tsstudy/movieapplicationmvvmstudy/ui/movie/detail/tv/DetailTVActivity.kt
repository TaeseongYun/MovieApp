package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv

import android.os.Bundle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_tv.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding.BaseBindingActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailTvBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.viewmodel.DetailTVInformationViewModel


class DetailTVActivity : BaseBindingActivity<TVResult, DetailTVInformationViewModel>() {

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
        tvViewModel.run {
            getDetailTV(detailTvArgs.detailTV.id)
            initHighOrderFunction()
        }
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

        favorite_btn.setOnClickListener {
            tvViewModel.loadLikeState(detailTvArgs.detailTV)
        }

        back_activity.setOnClickListener { finish() }
    }

    override fun DetailTVInformationViewModel.initHighOrderFunction() {
        detailTVResult = {
            detailTvArgs.detailTV
        }
    }
}
