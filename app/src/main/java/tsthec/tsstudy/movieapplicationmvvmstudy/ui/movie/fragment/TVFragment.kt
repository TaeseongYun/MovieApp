package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tv_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.TvFragmentLayoutBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.holder.TvRecyclerViewHolder
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.MovieNowPlayingViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.TVViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.scrollListener
import java.util.ArrayList

class TVFragment : BaseFragment(), TvRecyclerViewHolder.IShowDetailTv {

    private val tvRecyclerAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.TV, iShowDetailTV = this)
    }

    private val tvViewModel by stateViewModel<TVViewModel>()

    private lateinit var binding: TvFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding(inflater, R.layout.tv_fragment_layout, container)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = tvViewModel
        binding.executePendingBindings()

        LogUtil.d("Now Page in Real TvFragment -> ${savedInstanceState?.get("nowPage")}")
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (tvRepository.nextPage > 1) {
            tvRepository.nextPage = 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tv_recyclerView.removeOnScrollListener(tvScrollListener)
    }

    private val tvScrollListener =
        scrollListener { totalItemCount, visibleItem, firstViewItemIndex ->
            if (tvViewModel.isLoading.value != true && totalItemCount - 3 <= (visibleItem + firstViewItemIndex)) {
                tvViewModel.loadMoreTvPage(++tvRepository.nextPage)
            }
        }


    override fun onLowMemory() {
        super.onLowMemory()
        this.context?.let { Glide.get(it).clearMemory() }
    }

    override fun onClick(position: Int) {
        findNavController().navigate(
            R.id.detailTVActivity,
            bundleOf("detailTV" to tvRecyclerAdapter.getItem(position))
        )
    }
}