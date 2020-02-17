package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DefaultObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.ResourceObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subscribers.DefaultSubscriber
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.MovieFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.StarFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.TVFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.SearchViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.loadFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit


@Suppress("CAST_NEVER_SUCCEEDS")
class MovieMainActivity : BaseActivity() {

    init {

        val mapTestList = listOf("1", "2", "3", "4")

//        val observable = Observable.fromIterable(mapTestList)
//        val listData = listOf("1", "2", "3", "5")
        val observable = Observable.fromIterable(mapTestList).flatMap {
            Observable.just<String>("$it<> + $it<->")
        }

        val singleTest = Single.fromObservable(observable)

        disposable += observable
            .subscribeOn(Schedulers.io())
//            .map {
//                when (it.substring(0..0)) {
//                    "1" -> 1
//                    "2" -> 2
//                    "3" -> 3
//                    "4" -> 4
//                    else -> 5
//                }
//            }
            .subscribe({ LogUtil.d(it.toString()) }, {})
    }
//    .subscribe(
//    { LogUtil.d(it) },
//    {})
//        val source = Observable.concat(
//            Observable.timer(100L, TimeUnit.MILLISECONDS).map { listData[0] },
//            Observable.timer(100L, TimeUnit.MILLISECONDS).map { listData[1] },
//            Observable.timer(300L, TimeUnit.MILLISECONDS).map { listData[2] },
//            Observable.timer(300L, TimeUnit.MILLISECONDS).map { listData[3] }
//        ).debounce(200L, TimeUnit.MILLISECONDS)
//
//        disposable += source.subscribe {
//            LogUtil.d(it)
//        }
//        sleep(6000)


    private val movieFragment: MovieFragment by lazy {
        MovieFragment()
    }

    private val tvFragment: TVFragment by lazy {
        TVFragment()
    }

    private val starFragment: StarFragment by lazy {
        StarFragment()
    }

//    private val searchViewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable += backKeyPressed.toFlowable(BackpressureStrategy.BUFFER)
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .map { it.second - it.first < 2000L }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ boolean ->
                if (boolean) {
                    finish()
                } else {
                    toast("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
                }
            }, {
                it.printStackTrace()
            })

        movieFragment.setFragment()

        bottom_sheet_menu.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movie_menu -> {
                    movieFragment.setFragment()
                    true
                }
                R.id.tv_menu -> {
                    tvFragment.setFragment()
                    true
                }
                R.id.star_menu -> {
                    starFragment.setFragment()
                    true
                }

                else -> false
            }
        }

//        searchViewModel.onShowProgressBar = {
//            frameLayout.visibility = View.GONE
//            loading_progress.visibility = View.VISIBLE
//        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager).run {
            (menu?.findItem(R.id.app_bar_search)?.actionView as SearchView).run {
                queryHint = getString(R.string.query_string_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
//                        searchViewModel.nextSearch(query)
//                        searchViewModel.loadResult(1)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
//                        searchViewModel.nextSearch(newText)
//                        searchViewModel.loadResult(1)
                        return false
                    }

                })
            }
        }
        return true
    }

    override fun onBackPressed() {
        backKeyPressed.onNext(System.currentTimeMillis())
    }

    private fun Fragment.setFragment() {
        loadFragment(R.id.frameLayout, this)
    }

    override fun onStop() {
        LogUtil.d("onStop Called")
        super.onStop()
    }

    override fun onPause() {
        LogUtil.d("onPause Called")
        super.onPause()
    }
}
