package com.voitov.cryptoapp.domain.usecases

import com.voitov.cryptoapp.domain.CoinRepository

class LoadDataUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() {
        repository.loadData()
    }
}