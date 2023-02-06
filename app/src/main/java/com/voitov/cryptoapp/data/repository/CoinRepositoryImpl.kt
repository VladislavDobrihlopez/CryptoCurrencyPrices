package com.voitov.cryptoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.voitov.cryptoapp.data.datasource.LocalDataSource
import com.voitov.cryptoapp.data.datasource.RemoteDataSource
import com.voitov.cryptoapp.data.mapper.CoinInfoMapper
import com.voitov.cryptoapp.di.ApplicationScope
import com.voitov.cryptoapp.domain.CoinRepository
import com.voitov.cryptoapp.domain.entities.CoinInfo
import javax.inject.Inject

@ApplicationScope
class CoinRepositoryImpl @Inject constructor(
    private val mapper: CoinInfoMapper,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : CoinRepository {
    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return MediatorLiveData<List<CoinInfo>>().apply {
            addSource(localDataSource.getCoinInfoList()) {
                value = mapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(localDataSource.getCoinInfo(fromSymbol)) {
                value = mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun loadData() {
        remoteDataSource.loadData()
    }

    companion object {
        private const val TAG = "CoinRepositoryImpl"
        private const val TIME_INTERVAL_IN_MILLIS = 10000L
    }
}