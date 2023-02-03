package com.voitov.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.voitov.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.voitov.cryptoapp.domain.entities.CoinInfo
import com.voitov.cryptoapp.presentation.adapters.CoinInfoListAdapter

class CoinPriceListActivity : AppCompatActivity() {
    private val TAG = "CoinPriceListActivity"

    private lateinit var viewModel: CoinPriceListViewModel
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CoinPriceListViewModel::class.java)
        val adapter = CoinInfoListAdapter(this)
        binding.recyclerViewCoins.adapter = adapter
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
            adapter.submitList(it)
        })
    }
}