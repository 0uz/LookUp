package com.crypto.lookup.ui.dashboard

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R
import com.crypto.lookup.data.User
import com.crypto.lookup.data.UserFirebaseDaoImpl
import com.crypto.lookup.data.UserService
import com.crypto.lookup.data.listeners.onSaveDataListener
import kotlinx.android.synthetic.main.dashboard_coin_recycler_row.view.*

class SubscribedCoinAdapter(val user: User) : RecyclerView.Adapter<SubscribedCoinAdapter.SubscribedCoinsWH>() {

    var coinList = ArrayList<Coin>()
    var userService: UserService = UserService(UserFirebaseDaoImpl())

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
        holder.itemView.dashboardButton.setOnClickListener {
            userService.unsubscribeCoin(user.email, coinList.get(position).name, object : onSaveDataListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onSuccess() {
                    coinList.removeAt(position)
                    notifyDataSetChanged()
                }

                override fun onFailed(exception: Exception) {
                    throw exception
                }

            })
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun setDataList(data: ArrayList<Coin>) {
        this.coinList = data
    }
}