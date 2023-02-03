package com.voitov.cryptoapp.domain

import androidx.lifecycle.LiveData
import com.voitov.cryptoapp.domain.entities.CoinInfo

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>
    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>
    suspend fun loadData()
}