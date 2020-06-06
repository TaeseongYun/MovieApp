package tsthec.tsstudy.movieapplicationmvvmstudy

import android.app.Application
import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tsthec.tsstudy.movieapplicationmvvmstudy.di.*

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                databaseModel,
                apiModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                rxJavaModule
            )
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }
}