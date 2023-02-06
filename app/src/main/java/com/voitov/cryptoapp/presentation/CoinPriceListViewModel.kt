package com.voitov.cryptoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voitov.cryptoapp.domain.entities.CoinInfo
import com.voitov.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.voitov.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.voitov.cryptoapp.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class CoinPriceListViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase,
) : ViewModel() {

    fun getCoinData(): LiveData<List<CoinInfo>> {
        return getCoinInfoListUseCase.invoke()
    }

    fun getCoinDetails(fromSymbols: String): LiveData<CoinInfo> {
        return getCoinInfoUseCase.invoke(fromSymbols)
    }

    init {
        loadDataUseCase.invoke()
    }
}