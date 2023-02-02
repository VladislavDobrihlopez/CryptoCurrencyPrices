package com.voitov.cryptoapp.domain.entities

data class CoinInfo(
    val fromSymbol: String,
    val toSymbol: String,
    val price: Double,
    val lastUpdate: String,
    val lastTradeId: String? = null,
    val highDay: Double? = null,
    val lowDay: Double? = null,
    val lastMarket: String? = null,
    val imageUrl: String? = null,
)