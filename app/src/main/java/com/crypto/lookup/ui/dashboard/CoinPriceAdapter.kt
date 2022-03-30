package com.crypto.lookup.ui.dashboard
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import kotlinx.android.synthetic.main.coin_price_recycler_row.view.*



class CoinPriceAdapter(val coinNameList: ArrayList<String>, val coinPriceList : ArrayList<Double>) : RecyclerView.Adapter<CoinPriceAdapter.CoinsWH>() {

    class CoinsWH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsWH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.coin_price_recycler_row, parent, false)
        return CoinsWH(itemView)
    }

    override fun getItemCount(): Int {
        return coinNameList.size
    }

    override fun onBindViewHolder(holder: CoinsWH, position: Int) {

        holder.itemView.coinTextView.text=coinNameList.get(position)
        holder.itemView.priceTextView.text= coinPriceList.get(position).toString()

    }
}
