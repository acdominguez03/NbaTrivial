package com.example.nbatrivial

import android.app.Application
import com.example.nbatrivial.di.remoteModule
import com.example.nbatrivial.di.repositoriesModule
import com.example.nbatrivial.di.viewModelModule
import com.example.nbatrivial.utils.PreferenceService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NbaTrivialApp: Application() {

    override fun onCreate() {
        super.onCreate()

        PreferenceService.init(this)
        startKoin {
            // declare context
            androidContext(this@NbaTrivialApp)
            androidLogger()
            // declare modules
            modules(remoteModule, repositoriesModule, viewModelModule)
        }
    }
}