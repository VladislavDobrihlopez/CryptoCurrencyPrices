package com.voitov.cryptoapp.pojo.coins

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Datum(
    @SerializedName("CoinInfo")
    @Expose
    private val coinInfo: CoinInfo? = null
)