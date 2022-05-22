package com.crypto.lookup.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.crypto.lookup.data.Fear
import com.crypto.lookup.data.listeners.onGetNoDataListener
import kotlinx.coroutines.*
import org.json.JSONObject
import org.json.JSONTokener
import java.net.URL

class HomeViewModel : ViewModel() {
    var signalCoinListData: MutableLiveData<SignalCoinList>
    var signalCoinAdapter: SignalCoinAdapter
    private val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
        "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
        "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj"
    )
    private val restClient = factory.newRestClient()
    private val signalCoinService = SignalCoinService(SignalCoinFirebaseDaoImpl())
    var fearIndex: MutableLiveData<Fear>

    init {
        signalCoinListData = MutableLiveData()
        signalCoinAdapter = SignalCoinAdapter()
        fearIndex = MutableLiveData()
    }

    fun fakeData() {
        signalCoinService.fakeData() // TODO DELETE
    }


    fun setAdapterData(data: SignalCoinList) {
        signalCoinAdapter.signalCoins = data.signalCoins
        signalCoinAdapter.notifyDataSetChanged()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun dataUpdate(timeInterval: Long, data: ArrayList<SignalCoin>, listener: onGetNoDataListener): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {
                data.stream().filter { it.isOpen }.forEach {
                    val coin = restClient.get24HrPriceStatistics(it.symbol)
                    it.currentPrice = coin.lastPrice.toFloat()
                    signalCoinListData.postValue(SignalCoinList(data))
                    listener.onSuccess()
                }
                signalCoinListData.postValue(SignalCoinList(data))
                delay(timeInterval)
            }
        }
    }

    fun initFearIndex(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            val response = URL("https://api.alternative.me/fng/").readText()
            val jsonObject = JSONTokener(response).nextValue() as JSONObject
            var fear = Fear(
                jsonObject.getJSONArray("data").getJSONObject(0).getInt("value"),
                jsonObject.getJSONArray("data").getJSONObject(0).getString("value_classification")
            )
            fearIndex.postValue(fear)
        }


    }

}