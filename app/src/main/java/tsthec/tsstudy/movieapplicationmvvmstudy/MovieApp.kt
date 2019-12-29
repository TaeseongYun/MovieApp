package tsthec.tsstudy.movieapplicationmvvmstudy

import android.app.Application
import android.content.Context
import org.koin.core.context.startKoin
import tsthec.tsstudy.movieapplicationmvvmstudy.di.databaseModel

class MovieApp : Application() {
    companion object {
        private lateinit var instance: Application

        val context: Context
            get() = instance.applicationContext
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            listOf(databaseModel)
        }
    }
}