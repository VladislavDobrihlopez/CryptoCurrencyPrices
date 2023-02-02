package com.voitov.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class CoinDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinPriceListViewModel
    private lateinit var textViewPriceValue: TextView
    private lateinit var textViewMinValueForWholeDay: TextView
    private lateinit var textViewMaxValueForWholeDay: TextView
    private lateinit var textViewLastDeal: TextView
    private lateinit var textViewLastUpdateTime: TextView
    private lateinit var textViewFromSymbol: TextView
    private lateinit var textViewToSymbol: TextView
    private lateinit var imageViewCoinLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
        initViews()

        viewModel = ViewModelProvider(this).get(CoinPriceListViewModel::class.java)

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: ""

        viewModel.getCoinDetails(fromSymbol).observe(this) {
            Log.d(TAG, it.toString())
            textViewPriceValue.text = it.price.toString()
            textViewMinValueForWholeDay.text = it.lowDay.toString()
            textViewFromSymbol.text = it.fromSymbol
            textViewToSymbol.text = it.toSymbol
            textViewMaxValueForWholeDay.text = it.highDay.toString()
            textViewLastDeal.text = it.lastMarket
            textViewLastUpdateTime.text = it.getFormattedTime()
            Glide.with(this@CoinDetailsActivity)
                .load(it.getFullImageUrl())
                .into(imageViewCoinLogo)
        }
    }

    private fun initViews() {
        textViewPriceValue = findViewById(R.id.textViewPriceValue)
        textViewMinValueForWholeDay = findViewById(R.id.textViewMinValueForWholeDay)
        textViewMaxValueForWholeDay = findViewById(R.id.textViewMaxValueForWholeDay)
        textViewLastDeal = findViewById(R.id.textViewLastDeal)
        textViewLastUpdateTime = findViewById(R.id.textViewLastUpdateTime)
        textViewFromSymbol = findViewById(R.id.textViewFromSymbol)
        textViewToSymbol = findViewById(R.id.textViewToSymbol)
        imageViewCoinLogo = findViewById(R.id.imageViewCoinLogo)
    }

    companion object {
        private const val TAG = "CoinDetailsActivity"
        private const val EXTRA_FROM_SYMBOL = "FROM_SYMBOL"
        fun newIntent(context: Context, fromSymbol: String): Intent {
            return Intent(context, CoinDetailsActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            }
        }
    }
}