package com.voitov.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.voitov.cryptoapp.data.database.AppDatabase
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.data.network.ApiFactory
import com.voitov.cryptoapp.data.workers.RefreshDataWorkerFactory
import com.voitov.cryptoapp.di.DaggerApplicationComponent

class App : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    ApiFactory.apiService,
                    AppDatabase.getInstance(this).coinInfo(),
                    CoinInfoMapper(),
                )
            )
            .build()
    }
}