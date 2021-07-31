package id.haerulmuttaqin.cpn_training_kotlin

import android.app.Application
import id.haerulmuttaqin.data.di.databaseModule
import id.haerulmuttaqin.data.di.networkModule
import id.haerulmuttaqin.data.di.repositoryModule
import id.haerulmuttaqin.domain.di.useCaseModule
import id.haerulmuttaqin.cpn_training_kotlin.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { 
            androidContext(this@App)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    databaseModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}