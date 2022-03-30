package com.crypto.lookup.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    lateinit var coinListData: MutableLiveData<CoinList>
    lateinit var coinPriceAdapter: CoinPriceAdapter

    init {
        coinListData = MutableLiveData()
        coinPriceAdapter = CoinPriceAdapter()
        test()
    }

    fun setAdapterData(data: ArrayList<Coin>) {
        coinPriceAdapter.setDataList(data)
        coinPriceAdapter.notifyDataSetChanged()
    }

    fun test() {
        val y = arrayListOf<Coin>()
        val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
            "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
            "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj"
        )
        GlobalScope.launch {
            val client: BinanceApiRestClient = factory.newRestClient()
            val bookTickers = client.bookTickers
            for (ticker in bookTickers) {
                if (ticker.symbol.takeLast(4).equals("USDT") && ticker.bidPrice.toFloat() != 0F)
                    y.add(Coin(ticker.symbol, ticker.bidPrice.toFloat()))
            }
            coinListData.postValue(CoinList(y))
        }

    }
}