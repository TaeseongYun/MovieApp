package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.MovieFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.StarFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.TVFragment
//import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment.TVFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.util.loadFragment

@Suppress("CAST_NEVER_SUCCEEDS")
class MovieMainActivity : AppCompatActivity() {

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
//                    tvFragment.onDestroy()
//                    starFragment.onDestroy()
                    true
                }
                R.id.tv_menu -> {
                    tvFragment.setFragment()
//                    movieFragment.onDestroy()
//                    starFragment.onDestroy()
                    true
                }
                R.id.star_menu -> {
                    starFragment.setFragment()
//                    movieFragment.onDestroy()
//                    tvFragment.onDestroy()
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

    private fun Fragment.setFragment() {
        loadFragment(R.id.frameLayout, this)
    }
}
