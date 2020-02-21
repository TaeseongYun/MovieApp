package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
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
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import java.util.concurrent.TimeUnit


class DetailMovieActivity : BaseBindingActivity<MovieResult>() {

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
        loadDatabase()
        detailViewModel.getResultDetailMovie(getDetail(intent)?.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()

        setFavoriteButton {
            when (it) {
                true -> favorite_btn.setImageResource(R.drawable.ic_favorite_black_24dp)
                false -> favorite_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        }

        button.setOnClickListener { detailViewModel.nextWord(ed_text.text.toString()) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun viewBinding() {
        with(binding) {
            movieDetailResult = getDetail(intent)
            api = API
            vm = detailViewModel
            if (!getDetail(intent)?.backdrop_path.isNullOrEmpty())
                glideToolbar.loadMovieBackground(API.moviePhoto + getDetail(intent)?.backdrop_path)
            movie_img.loadMovieBackground(API.moviePhoto + getDetail(intent)?.posterPath)
            genre_recyclerView.run {
                adapter = genreRecyclerViewAdapter
                layoutManager = LinearLayoutManager(this.context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            lifecycleOwner = this@DetailMovieActivity
            executePendingBindings()
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun loadDatabase() = detailViewModel.getLoadDatabase(getDetail(intent)?.id)

    override fun setFavoriteButton(isLike: (Boolean) -> Unit) {
        detailViewModel.favoriteState.observe(this, Observer {
            isLike(it)
        })
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

    override fun getDetail(intent: Intent): MovieResult? =
        intent.getParcelableExtra(MOVIE)
}
