package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.star_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.StarFragmentLayoutBinding
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.MainRecyclerAdapter
import tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel.StarViewModel
import tsthec.tsstudy.movieapplicationmvvmstudy.util.IAdapterClass

class StarFragment : BaseFragment() {

    private lateinit var binding: StarFragmentLayoutBinding

    private val starViewModel by viewModel<StarViewModel>()

    private val starMovieRecyclerAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.MOVIE)
    }

    private val starTVRecyclerAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(AdapterViewType.DataType.TV)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, R.layout.star_fragment_layout, container)

        binding.vm = starViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner.onItemSelectedListener = object : IAdapterClass {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (parent?.selectedItemPosition) {
                    0 -> {
                        starViewModel.loadMovieDataFromDatabase()
                        viewINIT(0)
                    }
                    1 -> {
                        starViewModel.loadTvDataFromDatabase()
                        viewINIT(1)
                    }
                }
            }
        }
    }

    private fun viewINIT(
        position: Int,
        gridLayoutManager: GridLayoutManager = GridLayoutManager(this.context, 2)
    ) {
        when (position) {
            0 -> roomMovie_recyclerView.run {
                adapter = starMovieRecyclerAdapter
                layoutManager = gridLayoutManager
            }

            1 -> roomTV_recyclerView.run {
                adapter = starTVRecyclerAdapter
                layoutManager = gridLayoutManager
            }
        }
    }
}

