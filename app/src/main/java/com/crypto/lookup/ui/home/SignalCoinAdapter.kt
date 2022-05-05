package com.crypto.lookup.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import kotlinx.android.synthetic.main.home_signal_recycler_row.view.*
import java.text.SimpleDateFormat

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SignalWH, position: Int) {
        val signalCoin = signalCoins.get(position)
        holder.itemView.signalCoinName.text = signalCoin.symbol
        holder.itemView.signalCoinCurrentPrice.text =
            signalCoin.currentPrice.toString() + "$ %" + ((signalCoin.currentPrice - signalCoin.openPrice) / signalCoin.openPrice)
        holder.itemView.signalCoinOpenPrice.text = signalCoin.openPrice.toString() + "$"
        holder.itemView.signalOpenDate.text = SimpleDateFormat("dd/mm/yyyy hh:mm").format(signalCoin.openDate)

        if (signalCoin.currentPrice > signalCoin.openPrice) {
            holder.itemView.signalCoinCurrentPrice.setTextColor(Color.GREEN)
            holder.itemView.signalImage.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources,
                    R.drawable.increase,
                    null
                )
            )
        } else {
            holder.itemView.signalCoinCurrentPrice.setTextColor(Color.RED)
            holder.itemView.signalImage.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources,
                    R.drawable.decrease,
                    null
                )
            )
        }


    }


}