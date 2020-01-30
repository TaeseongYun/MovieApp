package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tv_fragment_layout.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.ViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.TvFragmentLayoutBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.PopularTVRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.TvRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.detail.tv.DetailTVActivity
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.TvNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.inject
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener

class TVFragment : BaseFragment(), TvRecyclerViewHolder.IShowDetailTv {

    private val tvRecyclerAdapter: PopularTVRecyclerAdapter by lazy {
        PopularTVRecyclerAdapter(ViewType.TV, this.context, this)
    }

    private lateinit var tvViewModel: TvNowPlayingViewModel

    private lateinit var binding: TvFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvViewModel = TvNowPlayingViewModel::class.java.inject(this) {
            TvNowPlayingViewModel(tvRepository)
        }

        binding = binding(inflater, R.layout.tv_fragment_layout, container)

        binding.lifecycleOwner = this
        binding.vm = tvViewModel
        binding.executePendingBindings()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tv_recyclerView.run {
            adapter = tvRecyclerAdapter
            layoutManager = GridLayoutManager(this.context, 2)
            addOnScrollListener(tvScrollListener)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tv_recyclerView.removeOnScrollListener(tvScrollListener)
        tvRecyclerAdapter.clearItems()
    }

    private val tvScrollListener =
        scrollListener { totalItemCount, visibleItem, firstViewItemIndex ->
            if (!tvViewModel.isLoading && totalItemCount - 3 <= (visibleItem + firstViewItemIndex)) {
                tvRepository.tvPage++
                tvViewModel.loadMoreTvPage()
            }

        }


    override fun onLowMemory() {
        super.onLowMemory()
        this.context?.let { Glide.get(it).clearMemory() }
    }

    override fun onClick(position: Int) {
        DetailTVActivity.getInstance(this.context, tvRecyclerAdapter.getItem(position))
    }
}