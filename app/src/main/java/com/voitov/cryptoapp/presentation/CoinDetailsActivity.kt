package com.voitov.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.voitov.cryptoapp.databinding.ActivityCoinDetailsBinding

class CoinDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinPriceListViewModel
    private val binding by lazy {
        ActivityCoinDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CoinPriceListViewModel::class.java)

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL

        viewModel.getCoinDetails(fromSymbol).observe(this) {
            Log.d(TAG, it.toString())
            with(binding) {
                textViewPriceValue.text = it.price.toString()
                textViewMinValueForWholeDay.text = it.lowDay.toString()
                textViewFromSymbol.text = it.fromSymbol
                textViewToSymbol.text = it.toSymbol
                textViewMaxValueForWholeDay.text = it.highDay.toString()
                textViewLastDeal.text = it.lastMarket
                textViewLastUpdateTime.text = it.lastUpdate

                Glide.with(this@CoinDetailsActivity)
                    .load(it.imageUrl)
                    .into(imageViewCoinLogo)
            }
        }
    }

    companion object {
        private const val TAG = "CoinDetailsActivity"
        private const val EXTRA_FROM_SYMBOL = "FROM_SYMBOL"
        private const val EMPTY_SYMBOL = ""
        fun newIntent(context: Context, fromSymbol: String): Intent {
            return Intent(context, CoinDetailsActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            }
        }
    }
}