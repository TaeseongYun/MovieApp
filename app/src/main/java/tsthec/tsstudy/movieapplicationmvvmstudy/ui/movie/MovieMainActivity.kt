package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.MovieFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.StarFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.TVFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.util.loadFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit


@Suppress("CAST_NEVER_SUCCEEDS")
class MovieMainActivity : BaseActivity() {
    init {
        val source1: Observable<Any> =
            Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map { i -> i * 100 }
                .map { i -> LogUtil.d("Source1 -> $i") }
                .take(10)

        val source2: Observable<Any> =
            Observable.interval(300L, TimeUnit.MILLISECONDS)
                .map { i -> i * 300 }
                .map { i -> LogUtil.d("Source2 -> $i") }
                .take(3)

        val source3: Observable<Any> =
            Observable.interval(2000L, TimeUnit.MILLISECONDS)
                .map { i -> i * 2000 }
                .map { i -> LogUtil.d("Source3 -> $i") }
                .take(2)

        disposable += Observable.concat(source1, source2, source3)
            .throttleLast(100L, TimeUnit.MILLISECONDS)
            .subscribe { it }

        sleep(6000)
    }

    //백버튼을 눌렀을 때 pair first 값과 second 두 개의 값을 emit 했을 때 2000(2초) 보다 적으면 finish()
    private val backKeyPressSubject = BehaviorSubject.create<Pair<Double, Double>>()

    private val movieFragment: MovieFragment by lazy {
        MovieFragment()
    }

    private val tvFragment: TVFragment by lazy {
        TVFragment()
    }

    private val starFragment: StarFragment by lazy {
        StarFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager).run {
            (menu?.findItem(R.id.app_bar_search)?.actionView as SearchView).run {
                queryHint = getString(R.string.query_string_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun Fragment.setFragment() {
        loadFragment(R.id.frameLayout, this)
    }
}

object Test {
    val hello = "This is Test"
}
