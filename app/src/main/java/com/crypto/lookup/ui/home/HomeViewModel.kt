package com.crypto.lookup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.market.CandlestickInterval
import com.github.mikephil.charting.data.CandleEntry

class HomeViewModel : ViewModel() {
    private val data : MutableLiveData<MutableList<CandleEntry>> by lazy {
        MutableLiveData<MutableList<CandleEntry>>().also {
            loadData()
        }
    }

    fun addData(data:CandleEntry){
        this.data.value?.add(data)
    }

    fun getData(): LiveData<MutableList<CandleEntry>> {
        return data;
    }

    private fun loadData() {
        val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
            "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
            "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj")
        val client: BinanceApiWebSocketClient = factory.newWebSocketClient()
        var counter = 1
        client.onCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE) {
            if (it.barFinal || counter == 1) {
                data.value?.add(
                    CandleEntry(
                        counter.toFloat(),
                        it.high.toFloat(),
                        it.low.toFloat(),
                        it.open.toFloat(),
                        it.close.toFloat()
                    )
                )
                counter++
            } else {
                data.value?.set(data.value!!.lastIndex, CandleEntry(
                    counter.toFloat(),
                    it.high.toFloat(),
                    it.low.toFloat(),
                    it.open.toFloat(),
                    it.close.toFloat()
                )
                )
            }
            println(data.value)

        }
    }


}