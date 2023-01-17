package com.voitov.cryptoapp.pojo.coinDetails

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoRawData(
    @SerializedName("")
    @Expose
    val coinPriceInfoJsonObject: JsonObject? = null
)