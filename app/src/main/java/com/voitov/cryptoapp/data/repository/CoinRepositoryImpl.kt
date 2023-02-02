package com.voitov.cryptoapp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.voitov.cryptoapp.data.database.AppDatabase
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.data.network.ApiFactory
import com.voitov.cryptoapp.domain.CoinRepository
import com.voitov.cryptoapp.domain.entities.CoinInfo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinRepositoryImpl(
    application: Application
) : CoinRepository {
    private val apiService = ApiFactory.apiService
    private val dao = AppDatabase.getInstance(application).coinInfo()
    private val mapper = CoinInfoMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
//        return MediatorLiveData<List<CoinInfo>>().apply {
//            addSource(dao.getCoinData()) {
//                mapper.mapDbModelListToEntityList(it)
//            }
//        }
        return Transformations.map(dao.getCoinData()) {
            mapper.mapDbModelListToEntityList(it)
        }
    }

    override fun loadData() {
        apiService.getListOfTopCoins().subscribeOn(Schedulers.io())
            .map { mapper.mapCoinNamesListDtoToString(it) }
            .flatMap { apiService.getCoinDetailsInfo(it) }
            .map { mapper.mapJsonObjectToDtoList(it) }
            .map { mapper.mapDtoListToDbModelList(it) }
            .delay(10, TimeUnit.SECONDS)
            .repeat()
            .subscribe({
                Log.d(TAG, it.toString())
                dao.insertCoinInfoList(it)
            }, {
                Log.d(TAG, it.toString())
            })
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
//        return MediatorLiveData<CoinInfo>().apply {
//            addSource(dao.getDetailedCoin(fromSymbol)) {
//                mapper.mapDbModelToEntity(it)
//            }
//        }
        return Transformations.map(dao.getDetailedCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    companion object {
        private const val TAG = "CoinRepositoryImpl"
    }
}