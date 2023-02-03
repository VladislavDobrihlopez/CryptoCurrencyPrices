package com.voitov.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.voitov.cryptoapp.R
import com.voitov.cryptoapp.databinding.CoinItemBinding
import com.voitov.cryptoapp.domain.entities.CoinInfo

class CoinInfoListAdapter(
    private val context: Context
) : ListAdapter<CoinInfo, CoinInfoListViewHolder>(CoinInfoDiffCallback()) {
    var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoListViewHolder {
        val binding = CoinItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoListViewHolder, position: Int) {
        val coinInfo = getItem(position)
        val lastUpdateTimePattern = context.resources.getString(R.string.template_last_update_time)
        val symbolsPattern = context.resources.getString(R.string.template_symbols)
        with(holder.binding) {
            textViewPrice.text = coinInfo.price.toString()
            textViewLastUpdateTime.text =
                String.format(lastUpdateTimePattern, coinInfo.lastUpdate)
            textViewSymbols.text =
                String.format(symbolsPattern, coinInfo.fromSymbol, coinInfo.toSymbol)
            Glide.with(root)
                .load(coinInfo.imageUrl)
                .into(imageViewCoinLogo)
            root.setOnClickListener {
                onClickListener?.onClick(coinInfo)
            }
        }
    }

    interface OnClickListener {
        abstract fun onClick(coinInfo: CoinInfo)
    }
}