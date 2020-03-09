package tsthec.tsstudy.movieapplicationmvvmstudy.rx

import io.reactivex.subjects.BehaviorSubject

object RxBusCls {
    private val databaseSubject = BehaviorSubject.create<Pair<() -> Unit, () -> Unit>>()

    fun publish(nextValue: Pair<() -> Unit, () -> Unit>) {
        databaseSubject.onNext(nextValue)
    }

    fun listen() = databaseSubject
}