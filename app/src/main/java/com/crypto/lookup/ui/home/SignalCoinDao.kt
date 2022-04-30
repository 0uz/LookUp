package com.crypto.lookup.ui.home

import com.crypto.lookup.data.listeners.onGetDataListListener
import com.crypto.lookup.data.listeners.onGetDataListener

interface SignalCoinDao {
    fun retrieve(coinList: SignalCoinList, listener: onGetDataListListener)
    fun fakeData()
}