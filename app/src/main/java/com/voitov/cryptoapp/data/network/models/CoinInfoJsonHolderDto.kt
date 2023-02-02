package com.voitov.cryptoapp.data.network.models

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonHolderDto(
    @SerializedName("RAW")
    @Expose
    val json: JsonObject? = null
)