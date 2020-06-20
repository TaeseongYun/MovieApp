package tsthec.tsstudy.movieapplicationmvvmstudy

import android.app.Application
import com.bumptech.glide.Glide
import com.tsdev.data.di.localDataSourceModule
import com.tsdev.data.di.networkModule
import com.tsdev.data.di.remoteModule
import com.tsdev.data.di.repositoryModule
import com.tsdev.domain.di.schedulersProviderModule
import com.tsdev.domain.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tsthec.tsstudy.movieapplicationmvvmstudy.di.*

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                networkModule,
                viewModelModule,
                rxJavaModule,
                remoteModule,
                localDataSourceModule,
                useCaseModule,
                schedulersProviderModule,
                repositoryModule
            )
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }
}