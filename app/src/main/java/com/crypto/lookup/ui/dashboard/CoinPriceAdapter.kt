package com.crypto.lookup.ui.dashboard
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import kotlinx.android.synthetic.main.coin_price_recycler_row.view.*



class CoinPriceAdapter() : RecyclerView.Adapter<CoinPriceAdapter.CoinsWH>() {
    var coinList = ArrayList<Coin>()

    class CoinsWH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setDataList(data: ArrayList<Coin>) {
        this.coinList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsWH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.coin_price_recycler_row, parent, false)
        return CoinsWH(itemView)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: CoinsWH, position: Int) {

        holder.itemView.coinTextView.text = coinList.get(position).name
        holder.itemView.priceTextView.text = coinList.get(position).price.toString()

    }
}
