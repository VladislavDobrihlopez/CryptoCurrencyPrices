package com.voitov.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CoinInfoDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getCoinData(): LiveData<List<CoinInfoDbModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol=:fromSymbol LIMIT 1")
    fun getDetailedCoin(fromSymbol: String): LiveData<CoinInfoDbModel>

    @Insert(onConflict = REPLACE)
    fun insertCoinInfoList(coinDetails: List<CoinInfoDbModel>)
}