package com.crypto.lookup.ui.home

import com.google.firebase.firestore.Exclude
import java.io.Serializable
import java.util.*

class SignalCoinList(val signalCoins: ArrayList<SignalCoin>)

data class SignalCoin(
    val symbol: String = "",
    val openPrice: Float = 0F,
    var closePrice: Float? = 0F,
    @get:Exclude
    var currentPrice: Float = 0F,
    val openDate: Date? = null,
    val closeDate: Date? = null,
    val isOpen: Boolean = true,
) : Serializable