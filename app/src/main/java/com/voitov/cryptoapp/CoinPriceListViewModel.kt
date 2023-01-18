package com.voitov.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.voitov.cryptoapp.api.ApiFactory
import com.voitov.cryptoapp.database.AppDatabase
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfo
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfoRawData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinPriceListViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "CoinPriceListViewModel"
    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)

    init {
        loadData()
    }

    fun getCoinData(): LiveData<List<CoinPriceInfo>> {
        return db.coinDetailsDao().getCoinData()
    }

    fun getCoinDetails(fromSymbols: String): LiveData<CoinPriceInfo> {
        return db.coinDetailsDao().getDetailedCoin(fromSymbols)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getListOfTopCoins(limit = 10)
            .map {
                it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: throw Exception(
                    NullPointerException()
                )
            }
            .flatMap {
                ApiFactory.apiService.getCoinDetailsInfo(fromSymbols = it, toSymbols = "USD")
            }
            .map {
                deserializeJson(it)
            }
            .delaySubscription(5, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinDetailsDao().saveCoinDetails(it)
                Log.d(TAG, it.toString())
            }, {
                Log.d(TAG, it.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun deserializeJson(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        jsonObject?.let {
            val coinKeySet = jsonObject.keySet()
            for (key in coinKeySet) {
                val currencyJson = jsonObject.getAsJsonObject(key)
                val currencyKeySet = currencyJson.keySet()
                for (currency in currencyKeySet) {
                    val coinPriceInfo: CoinPriceInfo =
                        Gson().fromJson(
                            currencyJson.getAsJsonObject(currency),
                            CoinPriceInfo::class.java
                        )
                    result.add(coinPriceInfo)
                }
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}