package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.ActivityMainBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.SearchMultiInformationViewHolder
//import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.SearchViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.BackKeyPressUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.loadNavigation
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.toast


class MovieMainActivity : BaseActivity(), SearchMultiInformationViewHolder.ISearchItem {

    private var navFragmentHost: NavHostFragment? = null

//    private val searchViewModel by viewModel<SearchViewModel>()

    private val binding by bindingBySetContent<ActivityMainBinding>(R.layout.activity_main)

    private val backKeyPressUtil: BackKeyPressUtil by inject {
        parametersOf(::finish, ::toast, disposable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewINIT()

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
        backKeyPressUtil.onBackKeyPress(System.currentTimeMillis())
    }

    override fun onStop() {
        LogUtil.d("onStop Called")
        super.onStop()
    }

    override fun viewINIT() {
        with(binding) {
            lifecycleOwner = this@MovieMainActivity
//            vm = searchViewModel
            executePendingBindings()
        }
    }

    override fun onPause() {
        LogUtil.d("onPause Called")
        super.onPause()
    }

    override fun onClickDetailView(position: Int) {
        LogUtil.d("clicked $position!!!")
    }
}
