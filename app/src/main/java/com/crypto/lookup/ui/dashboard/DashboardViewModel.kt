package com.crypto.lookup.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.crypto.lookup.data.User
import com.crypto.lookup.data.UserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    var coinListData: MutableLiveData<CoinList>
    lateinit var addCoinPriceAdapter: AddCoinPriceAdapter
    lateinit var subscribedCoinAdapter: SubscribedCoinAdapter
    lateinit var currentUser: User
    lateinit var userService: UserService

    init {
        coinListData = MutableLiveData()
        test()
    }

    fun setCurUser(user: User) {
        currentUser = user
        addCoinPriceAdapter = AddCoinPriceAdapter()
    }


    fun setAdapterData(data: ArrayList<Coin>) {
        addCoinPriceAdapter.setDataList(data)
        addCoinPriceAdapter.notifyDataSetChanged()
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
            subscribedCoinAdapter.coinList = y
        }
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
                if (ticker.symbol.takeLast(4).equals("USDT") &&
                    ticker.bidPrice.toFloat() != 0F &&
                    !ticker.symbol.contains("UP", ignoreCase = true) &&
                    !ticker.symbol.contains("DOWN", ignoreCase = true)
                )
                    y.add(Coin(ticker.symbol, ticker.bidPrice.toFloat()))
            }
            coinListData.postValue(CoinList(y))
        }

    }
}