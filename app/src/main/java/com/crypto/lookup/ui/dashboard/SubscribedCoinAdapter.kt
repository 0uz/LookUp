package com.crypto.lookup.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import kotlinx.android.synthetic.main.dashboard_coin_recycler_row.view.*

class SubscribedCoinAdapter() : RecyclerView.Adapter<SubscribedCoinAdapter.SubscribedCoinsWH>() {

    var coinList = ArrayList<Coin>()

    class SubscribedCoinsWH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribedCoinsWH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_coin_recycler_row, parent, false)
        return SubscribedCoinsWH(itemView)
    }

    override fun onBindViewHolder(holder: SubscribedCoinsWH, position: Int) {
        holder.itemView.coinTextView.text = coinList.get(position).name
        holder.itemView.priceTextView.text = coinList.get(position).price.toString()
        holder.itemView.dashboardButton.text = "Unsubscribe"


    }

    override fun getItemCount(): Int {
        return coinList.size
    }
}