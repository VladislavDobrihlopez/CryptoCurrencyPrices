package com.voitov.cryptoapp.pojo.coins

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListOfData(
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null,
)

