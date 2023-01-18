package com.voitov.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.voitov.cryptoapp.adapters.CoinPriceListAdapter
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {
    private val TAG = "CoinPriceListActivity"

    private lateinit var viewModel: CoinPriceListViewModel
    private lateinit var recyclerViewCoins: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        viewModel = ViewModelProvider(this).get(CoinPriceListViewModel::class.java)
        recyclerViewCoins = findViewById(R.id.recyclerViewCoins)
        val adapter = CoinPriceListAdapter(this)
        recyclerViewCoins.adapter = adapter

        viewModel.getCoinData().observe(this, Observer {
            Log.d(TAG, it.toString())
            adapter.coins = it
        })
    }
}