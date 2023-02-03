package com.voitov.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.voitov.cryptoapp.data.database.AppDatabase
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.data.network.ApiFactory
import com.voitov.cryptoapp.domain.CoinRepository
import com.voitov.cryptoapp.domain.entities.CoinInfo
import kotlinx.coroutines.delay
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinRepositoryImpl(
    application: Application
) : CoinRepository {
    private val apiService = ApiFactory.apiService
    private val dao = AppDatabase.getInstance(application).coinInfo()
    private val mapper = CoinInfoMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return MediatorLiveData<List<CoinInfo>>().apply {
            addSource(dao.getCoinData()) {
                value = mapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val coinNamesListDto = apiService.getListOfTopCoins()
                val fromSymbol = mapper.mapCoinNamesListDtoToString(coinNamesListDto)
                val coinInfoJsonHolder = apiService.getCoinDetailsInfo(fromSymbol)
                val coinInfoDtoList = mapper.mapJsonObjectToDtoList(coinInfoJsonHolder)
                val coinInfoDbModelList = mapper.mapDtoListToDbModelList(coinInfoDtoList)
                dao.insertCoinInfoList(coinInfoDbModelList)
            } catch (e: Exception) {
            }
            delay(TIME_INTERVAL_IN_MILLIS)
        }
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