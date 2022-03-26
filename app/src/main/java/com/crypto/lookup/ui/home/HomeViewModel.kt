package com.crypto.lookup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.market.CandlestickInterval
import com.github.mikephil.charting.data.CandleEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class HomeViewModel : ViewModel() {
    private val _data = MutableLiveData<MutableList<CandleEntry>>()

    init {
        loadPresentData()
    }

    fun getData(): LiveData<MutableList<CandleEntry>> {
        return _data;
    }

    fun loadPresentData() {
        GlobalScope.launch {
            val data = mutableListOf<CandleEntry>()
            val readText = URL("https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1m&limit=100").readText()
            val type = object : TypeToken<MutableList<MutableList<Any>>>() {}.type
            val fromJson = Gson().fromJson<MutableList<MutableList<Any>>>(readText, type)
            fromJson.forEachIndexed{ index, element ->
                var high = element[2] as String
                var low = element[3] as String
                var open = element[1] as String
                var close = element[4] as String
                data.add(
                    CandleEntry(
                        index.toFloat(),
                        high.toFloat(),
                        low.toFloat(),
                        open.toFloat(),
                        close.toFloat()
                    )
                )
            }
            _data.postValue(data)
            loadLiveData()
        }
    }
    fun loadLiveData() {
        val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
            "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
            "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj")
        val client: BinanceApiWebSocketClient = factory.newWebSocketClient()
        var counter = _data.value!!.size-1
        client.onCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE) {
            val data = _data.value
            if (it.barFinal) {
                data!!.add(
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
                data!!.set(_data.value!!.lastIndex, CandleEntry(
                    counter.toFloat(),
                    it.high.toFloat(),
                    it.low.toFloat(),
                    it.open.toFloat(),
                    it.close.toFloat()
                )
                )
            }
            _data.postValue(data)

        }
    }


}