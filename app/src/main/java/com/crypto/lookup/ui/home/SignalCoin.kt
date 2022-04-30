package com.crypto.lookup.ui.home

import java.util.Date

class SignalCoinList(val signalCoins : ArrayList<SignalCoin>)

data class SignalCoin(
    val symbol : String,
    val openPrice : Float?,
    val closePrice : Float?,
    val openDate: Date?,
    val closeDate: Date?,
)