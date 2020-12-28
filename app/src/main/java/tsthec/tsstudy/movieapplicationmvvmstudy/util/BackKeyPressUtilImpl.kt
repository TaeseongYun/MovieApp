package tsthec.tsstudy.movieapplicationmvvmstudy.util

import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import tsthec.tsstudy.movieapplicationmvvmstudy.R
import tsthec.tsstudy.movieapplicationmvvmstudy.constant.Const

class BackKeyPressUtilImpl(
    private val disposable: CompositeDisposable,
    finish: () -> Unit,
    toast: (Int, Int) -> Unit
) : BackKeyPressUtil {

    override val backKeyPressBehaviorSubject: BehaviorSubject<Long> =
        BehaviorSubject.createDefault(0L)

    init {
        disposable += backKeyPressBehaviorSubject
            .subscribeOn(Schedulers.computation())
            .buffer(2, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it[0] to it[1]
            }
            .subscribe({
                if (it.second - it.first > Const.BACK_KEY_INTERVAL) {
                    toast(R.string.back_key_press, Toast.LENGTH_LONG)
                } else {
                    finish()
                }
            }, {
                it.printStackTrace()
            })
    }


    override fun onBackKeyPress(nextTime: Long) {
        backKeyPressBehaviorSubject.onNext(nextTime)
    }
}