package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.data.source.repository.TvRepository
import org.koin.android.ext.android.inject


abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layout: Int) : Fragment() {

    internal lateinit var binding: T
        private set

    protected val movieRepository: MovieRepository by inject()

    protected val tvRepository: TvRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layout, container, false)
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
        return binding.root
    }

    fun bind(dataBinding: T.() -> Unit) {
        binding.run(dataBinding)
    }
}