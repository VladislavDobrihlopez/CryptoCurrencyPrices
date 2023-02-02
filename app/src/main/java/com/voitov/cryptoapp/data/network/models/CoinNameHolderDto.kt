package com.voitov.cryptoapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameHolderDto(
    @SerializedName("CoinInfo")
    @Expose
    val coinNameDto: CoinNameDto? = null
)
