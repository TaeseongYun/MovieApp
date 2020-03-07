package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.movie

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.savedstate.SavedStateRegistryOwner
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


    private val detailMovieArgs by navArgs<DetailMovieActivityArgs>()

    //savedState 사용을 위한 stateViewModel inject detailMovieArg 를 사용하기 위해 lateinit 으로 설정.
    private lateinit var detailViewModel: Lazy<DetailMovieInformationViewModel>
//    stateViewModel<DetailMovieInformationViewModel>()

    override fun viewINIT() {
        viewBinding()
        detailViewModel.run {
            if (value.saveGenreState != null) {
                LogUtil.d("saveGenreState Not NULL")
                value.loadSaveState()
            } else {
                LogUtil.d("saveGenreState  NULL")
                value.getResultDetailMovie(detailMovieArgs.detailMovie.id)
            }
//            value.initHighOrderFunction()
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
        detailViewModel =
            stateViewModel(bundle = bundleOf("detailMovie" to detailMovieArgs.detailMovie))

        viewINIT()

        favorite_btn.setOnClickListener { detailViewModel.value.changeLikeState(detailMovieArgs.detailMovie) }
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
            vm = detailViewModel.value
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
//        detailMovieResult = {
//            detailMovieArgs.detailMovie
//        }
    }
}
