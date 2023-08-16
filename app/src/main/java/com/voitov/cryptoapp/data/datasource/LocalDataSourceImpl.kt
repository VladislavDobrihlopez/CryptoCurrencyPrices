package com.voitov.cryptoapp.data.datasource

import androidx.lifecycle.LiveData
import com.voitov.cryptoapp.data.database.CoinInfoDao
import com.voitov.cryptoapp.data.database.CoinInfoDbModel
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: CoinInfoDao,
) : LocalDataSource {
    override fun getCoinInfoList(): LiveData<List<CoinInfoDbModel>> {
        return dao.getCoinData()
    }


    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoDbModel> {
        return dao.getDetailedCoin(fromSymbol)
    }
}