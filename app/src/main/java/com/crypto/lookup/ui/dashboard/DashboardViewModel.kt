package com.crypto.lookup.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    lateinit var coinListData: MutableLiveData<CoinList>
    lateinit var coinPriceAdapter: CoinPriceAdapter

    init {
        coinListData = MutableLiveData()
        coinPriceAdapter = CoinPriceAdapter()
    }

    fun setAdapterData(data: ArrayList<Coin>) {
        coinPriceAdapter.setDataList(data)
        coinPriceAdapter.notifyDataSetChanged()
    }

    fun test() {
        val x = CoinList(arrayListOf(Coin("BTC", 123F)))
        coinListData.postValue(x)
    }
}