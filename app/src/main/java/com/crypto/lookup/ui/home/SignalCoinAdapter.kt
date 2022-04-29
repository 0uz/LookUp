package com.crypto.lookup.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.crypto.lookup.R

class SignalCoinAdapter : RecyclerView.Adapter<SignalCoinAdapter.SignalWH>() {


    class SignalWH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalWH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_signal_recycler_row, parent, false)
        return SignalWH(itemView)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SignalWH, position: Int) {
        TODO("Not yet implemented")
    }

}