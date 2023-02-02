package com.voitov.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.voitov.cryptoapp.R
import com.voitov.cryptoapp.pojo.coinDetails.CoinPriceInfo

class CoinPriceListAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinPriceListAdapter.CoinPriceViewHolder>() {
    var onClickListener: OnClickListener? = null
    var coins: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinPriceViewHolder {
        return CoinPriceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CoinPriceViewHolder, position: Int) {
        val coinPriceInfo = coins[position]
        val lastUpdateTimePattern = context.resources.getString(R.string.template_last_update_time)
        val symbolsPattern = context.resources.getString(R.string.template_symbols)
        with(holder) {
            textViewPrice.text = coinPriceInfo.price.toString()
            textViewLastUpdateTime.text =
                String.format(lastUpdateTimePattern, coinPriceInfo.getFormattedTime())
            textViewSymbols.text =
                String.format(symbolsPattern, coinPriceInfo.fromSymbol, coinPriceInfo.toSymbol)
            Glide.with(itemView)
                .load(coinPriceInfo.getFullImageUrl())
                .into(imageViewCoinLogo)
            itemView.setOnClickListener {
                onClickListener?.onClick(coinPriceInfo)
            }
        }
    }

    override fun getItemCount() = coins.size

    inner class CoinPriceViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageViewCoinLogo: ImageView
        val textViewSymbols: TextView
        val textViewPrice: TextView
        val textViewLastUpdateTime: TextView

        init {
            imageViewCoinLogo = itemView.findViewById(R.id.imageViewCoinLogo)
            textViewSymbols = itemView.findViewById(R.id.textViewSymbols)
            textViewPrice = itemView.findViewById(R.id.textViewPrice)
            textViewLastUpdateTime = itemView.findViewById(R.id.textViewLastUpdateTime)
        }
    }

    interface OnClickListener {
        abstract fun onClick(coinPriceInfo: CoinPriceInfo)
    }
}