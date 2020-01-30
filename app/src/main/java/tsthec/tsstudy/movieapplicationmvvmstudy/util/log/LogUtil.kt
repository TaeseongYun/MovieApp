package tsthec.tsstudy.movieapplicationmvvmstudy.util.log

import android.util.Log
import java.lang.Exception

object LogUtil {
    private const val TAG = "MOVIE-APP"
    fun d(message: String?) =
        try {
            Log.d(TAG, message ?: "null")
        } catch (e: Exception) {
            e.printStackTrace()
        }
}