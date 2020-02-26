package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.SearchViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.loadNavigation
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import tsthec.tsstudy.movieapplicationmvvmstudy.util.toast


@Suppress("CAST_NEVER_SUCCEEDS")
class MovieMainActivity : BaseActivity() {

    init {

        val mapTestList = listOf("1", "2", "3", "4", "5")

//        val observable = Observable.fromIterable(mapTestList)
//        val listData = listOf("1", "2", "3", "5")
        val observable = Observable.fromIterable(mapTestList).flatMap {
            Observable.just<String>("$it<> + $it<->")
        }

        val singleTest = Single.fromObservable(observable)

        disposable += observable
            .subscribeOn(Schedulers.io())
            .buffer(3,4)
            .subscribe({ LogUtil.d(it.toString()) }, {})
    }

    private var navFragmentHost: NavHostFragment? = null
    private val searchViewModel by viewModel<SearchViewModel>()

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
                    this.toast("뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG)
                }
            }, {
                it.printStackTrace()
            })

        navFragmentHost = loadNavigation(R.id.nav_host_fragment)

        navFragmentHost?.let {
            NavigationUI.setupWithNavController(bottom_sheet_menu, it.navController)
        }
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

    override fun onStop() {
        LogUtil.d("onStop Called")
        super.onStop()
    }

    override fun onPause() {
        LogUtil.d("onPause Called")
        super.onPause()
    }
}
