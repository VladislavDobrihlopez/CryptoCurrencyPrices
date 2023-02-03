package com.voitov.cryptoapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNamesListDto(
    @SerializedName("Data")
    @Expose
    val names: List<CoinNameHolderDto>? = null,
)

