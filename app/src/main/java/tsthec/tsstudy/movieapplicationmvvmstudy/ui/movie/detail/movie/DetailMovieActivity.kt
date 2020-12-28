package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie

import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdev.data.source.MovieResult
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding.BaseBindingActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailMovieBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel.DetailMovieInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import java.util.concurrent.TimeUnit


class DetailMovieActivity : BaseBindingActivity<MovieResult, DetailMovieInformationViewModel>() {


    private val binding by binding<ActivityDetailMovieBinding>(R.layout.activity_detail_movie)

    private val genreRecyclerViewAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.GENRE)
    }


    private val detailMovieArgs by navArgs<DetailMovieActivityArgs>()

    private val detailViewModel by stateViewModel<DetailMovieInformationViewModel>()

    override fun viewINIT() {
        viewBinding()

        detailViewModel.run {
            getResultDetailMovie(detailMovieArgs.detailMovie.id)
            initHighOrderFunction()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Navigation Arg는 intent가 null이면 에러를 throw 한다.
        /*
        @MainThread
        inline fun <reified Args : NavArgs> Activity.navArgs() = NavArgsLazy(Args::class) {
            intent?.let { intent ->
                intent.extras ?: throw IllegalStateException("Activity $this has null extras in $intent")
            } ?: throw IllegalStateException("Activity $this has a null Intent")
        }
         */

        viewINIT()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun viewBinding() {
        with(binding) {
            movieDetailResult = detailMovieArgs.detailMovie
            api = API
            vm = detailViewModel
            lifecycleOwner = this@DetailMovieActivity
            executePendingBindings()
        }
        binding.genreRecyclerView.run {
            adapter = genreRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onPause() {
        LogUtil.d("onPause Called")
        super.onPause()
    }

    override fun onStop() {
        LogUtil.d("onStop Called")
        super.onStop()
    }

    override fun onDestroy() {
        LogUtil.d("onDestroy Called")
        super.onDestroy()
    }

    override fun DetailMovieInformationViewModel.initHighOrderFunction() {
        detailMovieResult = {
            detailMovieArgs.detailMovie
        }
    }
}
