package com.voitov.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.voitov.cryptoapp.data.database.AppDatabase
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.data.workers.RefreshDataWorker
import com.voitov.cryptoapp.domain.CoinRepository
import com.voitov.cryptoapp.domain.entities.CoinInfo

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {
    private val dao = AppDatabase.getInstance(application).coinInfo()
    private val mapper = CoinInfoMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return MediatorLiveData<List<CoinInfo>>().apply {
            addSource(dao.getCoinData()) {
                value = mapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(dao.getDetailedCoin(fromSymbol)) {
                value = mapper.mapDbModelToEntity(it)
            }
        }
    }

    companion object {
        private const val TAG = "CoinRepositoryImpl"
        private const val TIME_INTERVAL_IN_MILLIS = 10000L
    }
}