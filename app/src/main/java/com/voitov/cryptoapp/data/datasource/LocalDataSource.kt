package com.voitov.cryptoapp.data.datasource

import androidx.lifecycle.LiveData
import com.voitov.cryptoapp.data.database.CoinInfoDbModel
import com.voitov.cryptoapp.di.ApplicationScope

@ApplicationScope
interface LocalDataSource {
    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoDbModel>
    fun getCoinInfoList(): LiveData<List<CoinInfoDbModel>>
}