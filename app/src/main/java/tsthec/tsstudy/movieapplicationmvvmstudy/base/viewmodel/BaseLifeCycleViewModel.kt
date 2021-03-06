package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
//import io.reactivex.Maybe
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.schedulers.Schedulers
//import io.reactivex.subjects.BehaviorSubject
//import io.reactivex.subjects.PublishSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.rx.RxBusCls
import tsthec.tsstudy.movieapplicationmvvmstudy.util.log.LogUtil
import tsthec.tsstudy.movieapplicationmvvmstudy.util.plusAssign

abstract class BaseLifeCycleViewModel<T : Any?> : ViewModel() {
    val disposable = CompositeDisposable()

    internal val _isLoadingMutable = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _isLoadingMutable

//    val databaseSubject = BehaviorSubject.create<Pair<() -> Unit, () -> Unit>>()

    val uiBehaviorSubject = BehaviorSubject.create<T?>()

    /*
    BehaviorSubject -> subscribe 즉 구독자가 구독을 하면 제일 마지막에 들어온 데이터(onNext)를 발행한다.
     */

    val searchKeywordSubject = BehaviorSubject.create<String>()

    val testKeyword = PublishSubject.create<String>()

    init {
        disposable += RxBusCls.listen().observeOn(Schedulers.io())
            //map 형태로 첫 번쨰 매개변수는 Schedulers.io() 즉 백그라운드 스레드 에서 실행 why? db는 메인 스레드에서 실행 x
            .map { (first, second) ->
                first()
                second
            }
            .observeOn(AndroidSchedulers.mainThread())
            //구독 형태로 메인스레드에서 두 번째 매개변수 실행. why -> livedata는 백 스레드에서 실행 불가.
            .subscribe({ boolean ->
                boolean()
            }, {
                it.printStackTrace()
            })
    }

    override fun onCleared() {
        disposable.clear()
        LogUtil.d("$this 뷰모델 onCleared 호출")
        super.onCleared()
    }
}