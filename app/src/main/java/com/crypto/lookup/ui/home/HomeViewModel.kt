package com.crypto.lookup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.market.CandlestickInterval
import com.github.mikephil.charting.data.CandleEntry

class HomeViewModel : ViewModel() {
    private val _data = MutableLiveData<MutableList<CandleEntry>>()


    fun getData(): LiveData<MutableList<CandleEntry>> {
        return _data;
    }

    fun loadData() {
        val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
            "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
            "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj")
        val client: BinanceApiWebSocketClient = factory.newWebSocketClient()
        var counter = 1
        _data.value = mutableListOf(CandleEntry(
            1f,
            1f,
            1f,
            1f,
            1f
        ))
        client.onCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE) {
            val tempArr = mutableListOf<CandleEntry>()

            if (it.barFinal || counter == 1) {
                _data.value!!.add(
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
                _data.value!!.set(_data.value!!.lastIndex, CandleEntry(
                    counter.toFloat(),
                    it.high.toFloat(),
                    it.low.toFloat(),
                    it.open.toFloat(),
                    it.close.toFloat()
                )
                )
            }
            println(_data.value)
        }
    }


}