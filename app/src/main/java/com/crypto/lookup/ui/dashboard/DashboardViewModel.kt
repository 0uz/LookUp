package com.crypto.lookup.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.crypto.lookup.data.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    var coinListData: MutableLiveData<CoinList>
    var subscribedCoinData: MutableLiveData<CoinList>
    lateinit var addCoinPriceAdapter: AddCoinPriceAdapter
    lateinit var subscribedCoinAdapter: SubscribedCoinAdapter
    lateinit var currentUser: User

    init {
        coinListData = MutableLiveData()
        subscribedCoinData = MutableLiveData()
    }

    fun initializeAdaptersAndUser(user: User) {
        addCoinPriceAdapter = AddCoinPriceAdapter(user)
        subscribedCoinAdapter = SubscribedCoinAdapter(user)
        currentUser = user
    }

    fun setCoinAdapterData(data: ArrayList<Coin>) {
        addCoinPriceAdapter.setDataList(data)
        addCoinPriceAdapter.notifyDataSetChanged()
    }

    fun setSubscribedCoinAdapterData(data: ArrayList<Coin>) {
        subscribedCoinAdapter.setDataList(data)
        subscribedCoinAdapter.notifyDataSetChanged()
    }


    fun setSubscribedCoinsData() {
        val y = arrayListOf<Coin>()
        val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
            "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
            "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj"
        )
        GlobalScope.launch {
            val client: BinanceApiRestClient = factory.newRestClient()
            for (coin in currentUser.subscribedCoins) {
                y.add(Coin(coin, client.getPrice(coin).price.toFloat()))
            }
            subscribedCoinData.postValue(CoinList(y))
            setCoinListData()
        }
    }


    fun setCoinListData() {
        val y = arrayListOf<Coin>()
        val factory: BinanceApiClientFactory = BinanceApiClientFactory.newInstance(
            "QaTHifDPd0jcU4NlNwcf8DptOykOJISTtpcLqY5AC3UiKDB3yOGNGmxuhlcmmiN9",
            "P1DENk2ufvuvYHyFbv0iu7AvWFWIYmgRkJjN9YwXr3C0WxjCzmF9KOHlnMbi4fTj"
        )
        GlobalScope.launch {
            val client: BinanceApiRestClient = factory.newRestClient()
            val bookTickers = client.bookTickers
            for (ticker in bookTickers) {
                if (ticker.symbol.takeLast(4).equals("USDT") &&
                    ticker.bidPrice.toFloat() != 0F &&
                    !ticker.symbol.contains("UP", ignoreCase = true) &&
                    !ticker.symbol.contains("DOWN", ignoreCase = true) &&
                    subscribedCoinData.value!!.coins.all { !it.name.equals(ticker.symbol) }
                )
                    y.add(Coin(ticker.symbol, ticker.bidPrice.toFloat()))
            }
            coinListData.postValue(CoinList(y))
        }

    }
}