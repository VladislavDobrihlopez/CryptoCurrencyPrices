package com.voitov.cryptoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.voitov.cryptoapp.data.database.CoinInfoDao
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.data.network.ApiService
import javax.inject.Inject

class RefreshDataWorkerFactory @Inject constructor(
    private val apiService: ApiService,
    private val dao: CoinInfoDao,
    private val mapper: CoinInfoMapper,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return RefreshDataWorker(appContext, workerParameters, apiService, dao, mapper)
    }
}