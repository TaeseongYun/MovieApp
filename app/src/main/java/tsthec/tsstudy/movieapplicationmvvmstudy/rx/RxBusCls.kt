package tsthec.tsstudy.movieapplicationmvvmstudy.rx

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject


object RxBusCls {
    private val databaseSubject = BehaviorSubject.create<Pair<() -> Completable, () -> Unit>>()

    fun publish(nextValue: Pair<() -> Completable, () -> Unit>) {
        databaseSubject.onNext(nextValue)
    }

    fun listen() = databaseSubject
}