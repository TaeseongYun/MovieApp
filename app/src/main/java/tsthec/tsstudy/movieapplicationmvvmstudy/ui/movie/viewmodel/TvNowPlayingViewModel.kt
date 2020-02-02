//package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.viewmodel
//
//import androidx.lifecycle.MutableLiveData
//import tsthec.tsstudy.movieapplicationmvvmstudy.BuildConfig
//import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.BaseLifeCycleViewModel
//import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
//import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.MovieRepository
//import tsthec.tsstudy.movieapplicationmvvmstudy.data.source.TvRepository
//import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign
//import tsthec.tsstudy.movieapplicationmvvmstudy.util.with
//
//class TvNowPlayingViewModel(
//    private val tvRepository: TvRepository
//) : BaseLifeCycleViewModel() {
//
//
//    val tvList: MutableLiveData<List<TVResult>> by lazy {
//        MutableLiveData<List<TVResult>>()
//    }
//
//
//    init {
//        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY)
//            .with()
//            .doOnSubscribe {
//                _isLoadingMutable.value = true
//            }
//            .subscribe({
//                tvList.value = it.results
//                _isLoadingMutable.value = false
//            }, {
//                it.printStackTrace()
//            })
//    }
//
//    fun loadMoreTvPage() {
//        _isLoadingMutable.value = true
//        disposable += tvRepository.repositoryPopularTV(BuildConfig.MOVIE_API_KEY)
//            .with()
//            .subscribe({
//                tvList.value = it.results
//            }, { it.printStackTrace() })
//        _isLoadingMutable.value = false
//    }
//}