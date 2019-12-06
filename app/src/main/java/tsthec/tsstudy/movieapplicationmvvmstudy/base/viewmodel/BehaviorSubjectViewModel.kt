package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel


import io.reactivex.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResponse


abstract class BehaviorSubjectViewModel : BaseLifeCycleViewModel() {
    protected val uiBehaviorSubjectViewModel = BehaviorSubject.create<MovieResponse>()
}