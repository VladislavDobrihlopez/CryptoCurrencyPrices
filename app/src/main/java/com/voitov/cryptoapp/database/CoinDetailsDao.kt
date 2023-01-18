package com.voitov.cryptoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfo

@Dao
interface CoinDetailsDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getCoinData(): LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol=:fromSymbol LIMIT 1")
    fun getDetailedCoin(fromSymbol: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = REPLACE)
    fun saveCoinDetails(coinDetails: List<CoinPriceInfo>)
}