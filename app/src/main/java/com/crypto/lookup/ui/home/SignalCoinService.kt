package com.crypto.lookup.ui.home

import com.crypto.lookup.data.listeners.onGetDataListListener

class SignalCoinService(private val signalCoinDao: SignalCoinDao) {
    fun retrieve(signalCoin: ArrayList<String>, listener: onGetDataListListener) {
        signalCoinDao.retrieve(signalCoin, listener)
    }

    fun fakeData() {
        signalCoinDao.fakeData() // TODO DELETE
    }
}