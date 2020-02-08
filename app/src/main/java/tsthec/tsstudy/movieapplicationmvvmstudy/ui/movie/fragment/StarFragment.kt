package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseFragment
import tsthec.tsstudy.movieapplicationmvvmstudy.databinding.StarFragmentLayoutBinding

class StarFragment : BaseFragment() {

    private lateinit var binding: StarFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, R.layout.star_fragment_layout, container)

        return binding.root
    }
//        inflater.inflate(R.layout.star_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

