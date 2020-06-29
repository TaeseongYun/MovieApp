package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tsdev.data.source.repository.MovieRepository
import com.tsdev.data.source.repository.TvRepository
import org.koin.android.ext.android.inject


abstract class BaseFragment : Fragment() {

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        resId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)

    protected val movieRepository:  MovieRepository by inject()

    protected val tvRepository: TvRepository by inject()
}