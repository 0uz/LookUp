package com.crypto.lookup.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.crypto.lookup.data.User
import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.ui.home.SignalCoinFirebaseDaoImpl
import com.crypto.lookup.ui.home.SignalCoinService
import com.crypto.lookup.ui.login.UserViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    var coinListData: MutableLiveData<CoinList>
    var subscribedCoinData: MutableLiveData<CoinList>
    lateinit var addCoinPriceAdapter: AddCoinPriceAdapter
    lateinit var subscribedCoinAdapter: SubscribedCoinAdapter
    lateinit var currentUser: User
    private val signalCoinService = SignalCoinService(SignalCoinFirebaseDaoImpl())

    init {
        coinListData = MutableLiveData()
        subscribedCoinData = MutableLiveData()
    }

    fun initializeAdaptersAndUser(user: User, userViewModel: UserViewModel) {
        subscribedCoinAdapter = SubscribedCoinAdapter(user)
        addCoinPriceAdapter = AddCoinPriceAdapter(subscribedCoinAdapter, userViewModel)
        currentUser = user
    }

    fun updateCurrentUser(user: User){
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
                val coinData = client.get24HrPriceStatistics(coin)
                y.add(Coin(coin, coinData.lastPrice.toFloat()))
            }
            subscribedCoinData.postValue(CoinList(y))
            setCoinListData()
        }
    }


    fun setCoinListDataFirebase() {
        val y = arrayListOf<Coin>()
        val x = arrayListOf<Coin>()

        currentUser.subscribedCoins.forEach {
            x.add(Coin(it, 0F))
        }
        subscribedCoinData.postValue(CoinList(x))
        signalCoinService.retrieveCoins(object : onGetDataListener {
            override fun onSuccess(data: DocumentSnapshot) {
                val list: ArrayList<String> = data.get("coins") as ArrayList<String>
                list.forEach { coin ->
                    if (x.all { it.name == coin })
                        y.add(Coin(coin, 0F))
                }
                coinListData.postValue(CoinList(y))
            }

            override fun onFailed(e: Exception) {
            }

        })
    }


    fun setCoinListData() {
        val y = arrayListOf<Coin>()
        val x = arrayListOf<String>()
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