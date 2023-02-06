package com.voitov.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.voitov.cryptoapp.R
import com.voitov.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.voitov.cryptoapp.domain.entities.CoinInfo
import com.voitov.cryptoapp.presentation.adapters.CoinInfoListAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {
    private val TAG = "CoinPriceListActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CoinPriceListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CoinPriceListViewModel::class.java)
    }
    private lateinit var adapter: CoinInfoListAdapter
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        adapter = CoinInfoListAdapter(this)
        binding.recyclerViewCoins.adapter = adapter
        adapter.onClickListener = object : CoinInfoListAdapter.OnClickListener {
            override fun onClick(coinInfo: CoinInfo) {
                Log.d(TAG, coinInfo.toString())
                launchInCorrectMode(coinInfo.fromSymbol)
            }
        }
    }

    private fun setupViewModel() {
        viewModel.getCoinData().observe(this, Observer {
            Log.d(TAG, it.toString())
            adapter.submitList(it)
        })
    }

    private fun launchInCorrectMode(fromSymbol: String) {
        if (isOnePaneMode()) {
            launchNewScreen(fromSymbol)
        } else {
            launchInTabletMode(fromSymbol)
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainerSecondPart == null

    private fun launchInTabletMode(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragmentContainerSecondPart,
                CoinDetailsFragment.newInstance(fromSymbol)
            )
            .commit()
    }

    private fun launchNewScreen(fromSymbol: String) {
        startActivity(
            CoinDetailsActivity.newIntent(
                this@CoinPriceListActivity,
                fromSymbol
            )
        )
    }
}