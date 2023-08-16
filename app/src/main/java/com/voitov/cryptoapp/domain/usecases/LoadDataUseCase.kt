package com.voitov.cryptoapp.domain.usecases

import com.voitov.cryptoapp.domain.CoinRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke() {
        repository.loadData()
    }
}