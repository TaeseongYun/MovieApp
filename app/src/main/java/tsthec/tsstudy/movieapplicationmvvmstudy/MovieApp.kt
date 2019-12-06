package tsthec.tsstudy.movieapplicationmvvmstudy

import android.app.Application
import android.content.Context

class MovieApp : Application() {
    companion object {
        private lateinit var instance: Application

        val context: Context
            get() = instance.applicationContext
    }

    init {
        instance = this
    }
}