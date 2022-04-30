package com.crypto.lookup.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.crypto.lookup.data.listeners.onGetDataListener
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {
    var signalCoinListData : MutableLiveData<SignalCoinList>
    var signalCoinAdapter : SignalCoinAdapter
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

    fun fakeData(){
        signalCoinService.fakeData()
    }


    fun initData(data : ArrayList<SignalCoin>) {
        signalCoinAdapter.signalCoins = data
    }
    @OptIn(InternalCoroutinesApi::class)
    private fun dataUpdate(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {

                delay(timeInterval)
            }
        }
    }



}