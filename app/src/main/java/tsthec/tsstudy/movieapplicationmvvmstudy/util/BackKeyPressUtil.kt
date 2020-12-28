package tsthec.tsstudy.movieapplicationmvvmstudy.util

import io.reactivex.rxjava3.subjects.BehaviorSubject


interface BackKeyPressUtil {
    val backKeyPressBehaviorSubject: BehaviorSubject<Long>

    fun onBackKeyPress(nextTime: Long)
}