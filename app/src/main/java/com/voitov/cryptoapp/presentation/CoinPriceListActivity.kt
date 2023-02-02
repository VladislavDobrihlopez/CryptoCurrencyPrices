package com.voitov.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.voitov.cryptoapp.R
import com.voitov.cryptoapp.domain.entities.CoinInfo

class CoinPriceListActivity : AppCompatActivity() {
    private val TAG = "CoinPriceListActivity"

    private lateinit var viewModel: CoinPriceListViewModel
    private lateinit var recyclerViewCoins: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        viewModel = ViewModelProvider(this).get(CoinPriceListViewModel::class.java)
        recyclerViewCoins = findViewById(R.id.recyclerViewCoins)
        val adapter = CoinInfoListAdapter(this)
        recyclerViewCoins.adapter = adapter
        adapter.onClickListener = object : CoinInfoListAdapter.OnClickListener {
            override fun onClick(coinInfo: CoinInfo) {
                Log.d(TAG, coinInfo.toString())
                startActivity(
                    CoinDetailsActivity.newIntent(
                        this@CoinPriceListActivity,
                        coinInfo.fromSymbol
                    )
                )
            }
        }

        viewModel.getCoinData().observe(this, Observer {
            Log.d(TAG, it.toString())
            adapter.coins = it
        })
    }
}