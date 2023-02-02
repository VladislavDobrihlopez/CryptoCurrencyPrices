package com.voitov.cryptoapp.data.mapper

import com.google.gson.Gson
import com.voitov.cryptoapp.data.database.CoinInfoDbModel
import com.voitov.cryptoapp.data.network.ApiFactory
import com.voitov.cryptoapp.data.network.models.CoinInfoDto
import com.voitov.cryptoapp.data.network.models.CoinInfoJsonHolderDto
import com.voitov.cryptoapp.data.network.models.CoinNamesListDto
import com.voitov.cryptoapp.domain.entities.CoinInfo
import com.voitov.cryptoapp.utils.convertTimestampToTime

class CoinInfoMapper {
    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto): CoinInfoDbModel {
        return CoinInfoDbModel(
            fromSymbol = coinInfoDto.fromSymbol,
            toSymbol = coinInfoDto.toSymbol,
            price = coinInfoDto.price,
            lastUpdate = coinInfoDto.lastUpdate,
            lastTradeId = coinInfoDto.lastTradeId,
            highDay = coinInfoDto.highDay,
            lowDay = coinInfoDto.lowDay,
            lastMarket = coinInfoDto.lastMarket,
            imageUrl = coinInfoDto.imageUrl,
        )
    }

    fun mapDbModelToEntity(CoinInfoDbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            fromSymbol = CoinInfoDbModel.fromSymbol,
            toSymbol = CoinInfoDbModel.toSymbol,
            price = CoinInfoDbModel.price,
            lastUpdate = convertTimestampToTime(CoinInfoDbModel.lastUpdate),
            lastTradeId = CoinInfoDbModel.lastTradeId,
            highDay = CoinInfoDbModel.highDay,
            lowDay = CoinInfoDbModel.lowDay,
            lastMarket = CoinInfoDbModel.lastMarket,
            imageUrl = ApiFactory.BASE_IMAGE_URL + CoinInfoDbModel.imageUrl,
        )
    }

    fun mapJsonObjectToDtoList(coinInfoJsonHolderDto: CoinInfoJsonHolderDto): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = coinInfoJsonHolderDto.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (key in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(key)
            val currencyKeySet = currencyJson.keySet()
            for (currency in currencyKeySet) {
                val coinPriceInfo: CoinInfoDto =
                    Gson().fromJson(
                        currencyJson.getAsJsonObject(currency),
                        CoinInfoDto::class.java
                    )
                result.add(coinPriceInfo)
            }
        }
        return result
    }

    fun mapCoinNamesListDtoToString(coinNamesListDto: CoinNamesListDto): String {
        return coinNamesListDto.names?.map { it.coinNameDto?.name }?.joinToString(",")
            ?: throw Exception(
                NullPointerException("coinNamesListDto unexceptionably is null")
            )
    }

    fun mapDtoListToDbModelList(dtoList: List<CoinInfoDto>): List<CoinInfoDbModel> {
        return dtoList.map { mapDtoToDbModel(it) }
    }

    fun mapDbModelListToEntityList(dbModelList: List<CoinInfoDbModel>): List<CoinInfo> {
        return dbModelList.map { mapDbModelToEntity(it) }
    }
}