package com.crypto.lookup.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {
    var signalCoinListData: MutableLiveData<SignalCoinList>
    var signalCoinAdapter: SignalCoinAdapter
    private val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
        "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
        "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj"
    )
    private val restClient = factory.newRestClient()
    private val signalCoinService = SignalCoinService(SignalCoinFirebaseDaoImpl())

    init {
        signalCoinListData = MutableLiveData()
        signalCoinAdapter = SignalCoinAdapter()
    }

    fun fakeData() {
        signalCoinService.fakeData() // TODO DELETE
    }


    fun setAdapterData(data: SignalCoinList) {
        signalCoinAdapter.signalCoins = data.signalCoins
        signalCoinAdapter.notifyDataSetChanged()
    }


    @OptIn(InternalCoroutinesApi::class)
    fun dataUpdate(timeInterval: Long, data: ArrayList<SignalCoin>): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {
                Log.w("DATA UPDATE", "")
                data.forEach {
                    val coin = restClient.get24HrPriceStatistics(it.symbol)
                    it.currentPrice = coin.lastPrice.toFloat()
                }
                signalCoinListData.postValue(SignalCoinList(data))
                delay(timeInterval)
            }
        }
    }



}