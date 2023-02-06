package com.voitov.cryptoapp.di

import com.voitov.cryptoapp.data.repository.CoinRepositoryImpl
import com.voitov.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}