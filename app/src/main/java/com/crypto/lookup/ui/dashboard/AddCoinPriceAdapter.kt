package com.crypto.lookup.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import com.crypto.lookup.data.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_coin_price_recycler_row.view.*
import java.util.*


class AddCoinPriceAdapter(val user: User) : RecyclerView.Adapter<AddCoinPriceAdapter.CoinsWH>(), Filterable {
    var coinList = ArrayList<Coin>()
    var coinFilterList = ArrayList<Coin>()
    var firebaseInstance = Firebase.firestore.collection("users").document(user.email)


    class CoinsWH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setDataList(data: ArrayList<Coin>) {
        this.coinList = data
        coinFilterList = coinList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsWH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_coin_price_recycler_row, parent, false)
        return CoinsWH(itemView)
    }

    override fun getItemCount(): Int {
        return coinFilterList.size
    }

    override fun onBindViewHolder(holder: CoinsWH, position: Int) {

        holder.itemView.coinTextView.text = coinList.get(position).name
        holder.itemView.priceTextView.text = coinList.get(position).price.toString()
        holder.itemView.addButton.setOnClickListener {

        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    coinFilterList = coinList
                } else {
                    val resultList = ArrayList<Coin>()
                    for (row in coinList) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    coinFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = coinFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                coinFilterList = results?.values as ArrayList<Coin>
                notifyDataSetChanged()
            }

        }
    }

}
