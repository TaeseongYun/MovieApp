package tsthec.tsstudy.movieapplicationmvvmstudy.util

import io.reactivex.subjects.BehaviorSubject

interface BackKeyPressUtil {
    val backKeyPressBehaviorSubject: BehaviorSubject<Long>

    fun onBackKeyPress(nextTime: Long)
}