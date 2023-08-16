package com.voitov.cryptoapp.data.workers

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.voitov.cryptoapp.data.database.CoinInfoDao
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val apiService: ApiService,
    private val dao: CoinInfoDao,
    private val mapper: CoinInfoMapper,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        while (true) {
            Log.d(TAG, "doWork")
            try {
                val coinNamesListDto = apiService.getListOfTopCoins()
                val fromSymbol = mapper.mapCoinNamesListDtoToString(coinNamesListDto)
                val coinInfoJsonHolder = apiService.getCoinDetailsInfo(fromSymbol)
                val coinInfoDtoList = mapper.mapJsonObjectToDtoList(coinInfoJsonHolder)
                val coinInfoDbModelList = mapper.mapDtoListToDbModelList(coinInfoDtoList)
                dao.insertCoinInfoList(coinInfoDbModelList)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
            delay(TIME_INTERVAL_IN_MILLIS)
        }
    }

    companion object {
        private const val TAG = "RefreshDataWorker"
        private const val TIME_INTERVAL_IN_MILLIS = 10000L
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .apply {
                    setConstraints(createConstraints())
                }.build()
        }

        private fun createConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                .setRequiresBatteryNotLow(true)
                .build()
        }
    }
}