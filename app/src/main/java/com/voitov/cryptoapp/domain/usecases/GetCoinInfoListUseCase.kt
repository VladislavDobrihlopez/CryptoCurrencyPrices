package com.voitov.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.voitov.cryptoapp.domain.CoinRepository
import com.voitov.cryptoapp.domain.entities.CoinInfo

class GetCoinInfoListUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(): LiveData<List<CoinInfo>> {
        return coinRepository.getCoinInfoList()
    }
}