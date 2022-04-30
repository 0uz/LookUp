package com.crypto.lookup.ui.home

import com.crypto.lookup.data.listeners.onGetDataListListener

interface SignalCoinDao {
    fun retrieve(signalCoin: ArrayList<String>, listener: onGetDataListListener)
    fun fakeData() // TODO DELETE
}