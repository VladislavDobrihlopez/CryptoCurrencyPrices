package com.voitov.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.voitov.cryptoapp.data.repository.CoinRepositoryImpl
import com.voitov.cryptoapp.domain.entities.CoinInfo
import com.voitov.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.voitov.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.voitov.cryptoapp.domain.usecases.LoadDataUseCase

class CoinPriceListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoinRepositoryImpl(application)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    fun getCoinData(): LiveData<List<CoinInfo>> {
        return getCoinInfoListUseCase.invoke()
    }

    fun getCoinDetails(fromSymbols: String): LiveData<CoinInfo> {
        return getCoinInfoUseCase.invoke(fromSymbols)
    }

    init {
        loadData()
    }

    private fun loadData() {
        loadDataUseCase()
    }
}