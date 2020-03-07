package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.api.API
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.basebinding.BaseBindingActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityDetailMovieBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie.viewmodel.DetailMovieInformationViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import java.util.concurrent.TimeUnit


class DetailMovieActivity : BaseBindingActivity<MovieResult, DetailMovieInformationViewModel>() {

    init {
        val testSubject = BehaviorSubject.createDefault(1)

        disposable += testSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtil.d("3 - This is $it")
            }, {
                it.printStackTrace()
            })

        disposable += Observable.interval(1000L, TimeUnit.MICROSECONDS)
            .zipWith(
                Observable.just("Hello"), BiFunction<Long, String, String> { _, t2 -> t2 }
            )
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtil.d("This is $it")
            }, { it.printStackTrace() })

        testSubject.onNext(4)

        testSubject.onNext(5)

        disposable += testSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtil.d("1 - This is $it")
            }, {
                it.printStackTrace()
            })

        testSubject.onNext(2)

        testSubject.onNext(7)

        disposable += testSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtil.d("2 - This is $it")
            }, {
                it.printStackTrace()
            })
    }

    private val binding by binding<ActivityDetailMovieBinding>(R.layout.activity_detail_movie)

    private val genreRecyclerViewAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.GENRE)
    }

    //savedState 사용을 위한 stateViewModel inject
    private val detailViewModel by stateViewModel<DetailMovieInformationViewModel>()

    private val detailMovieArgs by navArgs<DetailMovieActivityArgs>()

    override fun viewINIT() {
        viewBinding()
        detailViewModel.run {
            if (saveGenreState != null) {
                LogUtil.d("saveGenreState Not NULL")
                loadSaveState()
            }

            else {
                LogUtil.d("saveGenreState  NULL")
                getResultDetailMovie(detailMovieArgs.detailMovie.id)
            }
            initHighOrderFunction()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewINIT()

        favorite_btn.setOnClickListener { detailViewModel.changeLikeState(detailMovieArgs.detailMovie) }

        LogUtil.d("What is detailMovie -> ${detailMovieArgs.detailMovie}")
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
        genre_recyclerView.run {
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
