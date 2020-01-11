package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.tv_fragment_layout.*
import org.jetbrains.anko.support.v4.startActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase
import tsthec.tsstudy.movieapplicationmvvmstudy.network.RetrofitObject
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.PopularTVRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.DetailTVActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.inject
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

class TVFragment : Fragment() {
    private val tvRecyclerAdapter: PopularTVRecyclerAdapter by lazy {
        PopularTVRecyclerAdapter(ViewType.TV, this.context)
    }

    private lateinit var tvViewModel: MovieNowPlayingViewModel

    private val tvRepository: MovieRepository by lazy {
        MovieRepository.getInstance(RetrofitObject.movieAPI, tvDatabase)
    }

    private val tvDatabase: MovieDatabase by lazy {
        MovieDatabase.getInstance(this.context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvViewModel = MovieNowPlayingViewModel::class.java.inject(this) {
            MovieNowPlayingViewModel(tvRepository, tvRecyclerAdapter, ViewType.TV)
        }

        viewInit()

        return inflater.inflate(R.layout.tv_fragment_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tvViewModel.loadPopularTV()

        tv_recyclerView.run {
            adapter = tvRecyclerAdapter
            layoutManager = GridLayoutManager(this.context, 2)
            addOnScrollListener(tvScrollListener)
        }

        tvViewModel.popularTvListData.observe(this, Observer {
            if (it.second != null) {
                startActivity<DetailTVActivity>("tvID" to it.second)
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tv_recyclerView.removeOnScrollListener(tvScrollListener)
        tvViewModel.clear()
    }

    private val tvScrollListener =
        scrollListener { totalItemCount, visibleItem, firstViewItemIndex ->
            if (!tvViewModel.isLoading && totalItemCount - 3 <= (visibleItem + firstViewItemIndex))
                tvViewModel.loadPopularTV()
        }

    private fun viewInit() {
        tvViewModel.hideProgressBar = {
            loading_group.visibility = View.GONE
        }
        tvViewModel.showProgressBar = {
            loading_group.visibility = View.VISIBLE
        }
    }
}