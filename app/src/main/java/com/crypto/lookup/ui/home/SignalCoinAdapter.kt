package com.crypto.lookup.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import kotlinx.android.synthetic.main.home_signal_recycler_row.view.*

class SignalCoinAdapter : RecyclerView.Adapter<SignalCoinAdapter.SignalWH>() {
    var signalCoins = ArrayList<SignalCoin>()

    class SignalWH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalWH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_signal_recycler_row, parent, false)
        return SignalWH(itemView)
    }

    override fun getItemCount(): Int {
        return signalCoins.size
    }

    override fun onBindViewHolder(holder: SignalWH, position: Int) {
        val signalCoin = signalCoins.get(position)
        holder.itemView.signalCoinName.text = signalCoin.symbol
        holder.itemView.signalCoinPrice.text = signalCoin.currentPrice.toString()
        holder.itemView.signalOpenDate.text = signalCoin.openDate.toString()
        if (signalCoin.currentPrice > signalCoin.openPrice)
            holder.itemView.signalImage.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources,
                    R.drawable.increase,
                    null
                )
            )
        else
            holder.itemView.signalImage.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources,
                    R.drawable.decrease,
                    null
                )
            )

    }


}