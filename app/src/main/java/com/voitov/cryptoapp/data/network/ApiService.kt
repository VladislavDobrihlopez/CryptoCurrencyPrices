package com.voitov.cryptoapp.data.network

import com.voitov.cryptoapp.BuildConfig
import com.voitov.cryptoapp.data.network.models.CoinInfoJsonHolderDto
import com.voitov.cryptoapp.data.network.models.CoinNamesListDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    fun getListOfTopCoins(
        @Query(QUERY_PARAM_LIMIT) limit: Int = LIMIT_DEFAULT,
        @Query(QUERY_PARAM_TO_SYMBOL) toSymbol: String = TO_SYMBOL_DEFAULT,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY_DEFAULT,
    ): Single<CoinNamesListDto>

    @GET("pricemultifull")
    fun getCoinDetailsInfo(
        @Query(QUERY_PARAM_FROM_SYMBOLS) fromSymbols: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) toSymbols: String = DEFAULT_TO_SYMBOLS,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY_DEFAULT,
    ): Single<CoinInfoJsonHolderDto>

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val LIMIT_DEFAULT = 30
        private const val TO_SYMBOL_DEFAULT = "USD"

        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"

        private const val API_KEY_DEFAULT = BuildConfig.API_KEY
        private const val DEFAULT_TO_SYMBOLS = "USD"
    }
}