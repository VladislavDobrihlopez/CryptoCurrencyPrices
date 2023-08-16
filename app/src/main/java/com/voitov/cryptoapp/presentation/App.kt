package com.voitov.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.voitov.cryptoapp.data.workers.RefreshDataWorkerFactory
import com.voitov.cryptoapp.di.DaggerApplicationComponent
import javax.inject.Inject

class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: RefreshDataWorkerFactory
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}