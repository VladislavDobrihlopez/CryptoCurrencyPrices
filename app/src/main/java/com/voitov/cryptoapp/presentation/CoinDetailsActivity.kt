package com.voitov.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.voitov.cryptoapp.R
import com.voitov.cryptoapp.databinding.ActivityCoinDetailsBinding

class CoinDetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCoinDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainerCoinDetails,
                    CoinDetailsFragment.newInstance(fromSymbol)
                )
                .commit()
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