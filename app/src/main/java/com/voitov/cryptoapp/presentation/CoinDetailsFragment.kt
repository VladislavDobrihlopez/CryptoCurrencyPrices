package com.voitov.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.voitov.cryptoapp.databinding.FragmentCoinDetailsBinding


class CoinDetailsFragment : Fragment() {
    private var _binding: FragmentCoinDetailsBinding? = null
    private val binding: FragmentCoinDetailsBinding
        get() = _binding ?: throw RuntimeException()
    private lateinit var viewModel: CoinPriceListViewModel
    private lateinit var fromSymbol: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    private fun parseArguments() {
        val arguments = requireArguments()
        if (!arguments.containsKey(KEY_FROM_SYMBOL)) {
            throw RuntimeException("Param $KEY_FROM_SYMBOL is absent")
        }
        fromSymbol = arguments.getString(KEY_FROM_SYMBOL, EMPTY_SYMBOL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinPriceListViewModel::class.java)

        viewModel.getCoinDetails(fromSymbol).observe(viewLifecycleOwner) {
            Log.d(TAG, it.toString())
            with(binding) {
                textViewPriceValue.text = it.price.toString()
                textViewMinValueForWholeDay.text = it.lowDay.toString()
                textViewFromSymbol.text = it.fromSymbol
                textViewToSymbol.text = it.toSymbol
                textViewMaxValueForWholeDay.text = it.highDay.toString()
                textViewLastDeal.text = it.lastMarket
                textViewLastUpdateTime.text = it.lastUpdate
                scrollViewCoinDetails.fullScroll(ScrollView.FOCUS_DOWN)
                Glide.with(this@CoinDetailsFragment)
                    .load(it.imageUrl)
                    .into(imageViewCoinLogo)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "CoinDetailsFragment"
        private const val KEY_FROM_SYMBOL = "FROM_SYMBOL"
        private const val EMPTY_SYMBOL = ""
        fun newInstance(fromSymbol: String): CoinDetailsFragment {
            return CoinDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}