package com.voitov.cryptoapp.di

import android.app.Application
import com.voitov.cryptoapp.data.database.AppDatabase
import com.voitov.cryptoapp.data.database.CoinInfoDao
import com.voitov.cryptoapp.data.datasource.LocalDataSource
import com.voitov.cryptoapp.data.datasource.LocalDataSourceImpl
import com.voitov.cryptoapp.data.datasource.RemoteDataSource
import com.voitov.cryptoapp.data.datasource.RemoteDataSourceImpl
import com.voitov.cryptoapp.data.network.ApiFactory
import com.voitov.cryptoapp.data.network.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    companion object {
        @ApplicationScope
        @Provides
        fun provideDao(
            application: Application,
        ): CoinInfoDao {
            return AppDatabase.getInstance(application).coinInfo()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}