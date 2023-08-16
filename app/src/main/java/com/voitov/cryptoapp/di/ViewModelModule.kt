package com.voitov.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.voitov.cryptoapp.presentation.CoinPriceListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(CoinPriceListViewModel::class)
    @Binds
    fun bindCoinPriceListViewModel(coinPriceListViewModel: CoinPriceListViewModel): ViewModel
}