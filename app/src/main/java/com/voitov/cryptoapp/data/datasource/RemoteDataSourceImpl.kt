package com.voitov.cryptoapp.data.datasource

import android.app.Application
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.voitov.cryptoapp.data.network.ApiService
import com.voitov.cryptoapp.data.workers.RefreshDataWorker
import com.voitov.cryptoapp.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class RemoteDataSourceImpl @Inject constructor(
    private val application: Application,
) : RemoteDataSource {
    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}