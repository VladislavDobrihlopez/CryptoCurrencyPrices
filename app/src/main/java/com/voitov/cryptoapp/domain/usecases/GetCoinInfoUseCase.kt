package com.voitov.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.voitov.cryptoapp.domain.CoinRepository
import com.voitov.cryptoapp.domain.entities.CoinInfo

class GetCoinInfoUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(fromSymbol: String): LiveData<CoinInfo> {
        return coinRepository.getCoinInfo(fromSymbol)
    }
}