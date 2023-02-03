package com.voitov.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String,
    val price: Double,
    val lastUpdate: Long? = null,
    val lastTradeId: String? = null,
    val highDay: Double? = null,
    val lowDay: Double? = null,
    val lastMarket: String? = null,
    val imageUrl: String,
)