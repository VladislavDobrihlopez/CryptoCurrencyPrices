package com.voitov.cryptoapp.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.voitov.cryptoapp.R
import com.voitov.cryptoapp.domain.entities.CoinInfo

class CoinInfoListAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoListAdapter.CoinInfoListViewHolder>() {
    var onClickListener: OnClickListener? = null
    var coins: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoListViewHolder {
        return CoinInfoListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CoinInfoListViewHolder, position: Int) {
        val coinInfo = coins[position]
        val lastUpdateTimePattern = context.resources.getString(R.string.template_last_update_time)
        val symbolsPattern = context.resources.getString(R.string.template_symbols)
        with(holder) {
            textViewPrice.text = coinInfo.price.toString()
            textViewLastUpdateTime.text =
                String.format(lastUpdateTimePattern, coinInfo.lastUpdate)
            textViewSymbols.text =
                String.format(symbolsPattern, coinInfo.fromSymbol, coinInfo.toSymbol)
            Glide.with(itemView)
                .load(coinInfo.imageUrl)
                .into(imageViewCoinLogo)
            itemView.setOnClickListener {
                onClickListener?.onClick(coinInfo)
            }
        }
    }

    override fun getItemCount() = coins.size

    inner class CoinInfoListViewHolder(itemView: View) :
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
        abstract fun onClick(coinInfo: CoinInfo)
    }
}