package com.crypto.lookup.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.market.CandlestickInterval
import com.crypto.lookup.R
import kotlinx.android.synthetic.main.home_signal_recycler_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Closeable
import java.util.*
import kotlin.collections.ArrayList

class SignalCoinAdapter : RecyclerView.Adapter<SignalCoinAdapter.SignalWH>() {
    var signalCoins = ArrayList<SignalCoin>()
    var candlestickEvent = ArrayList<Closeable>()

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
        holder.itemView.signalCoinPrice.text = signalCoin.closePrice.toString()
    }


}