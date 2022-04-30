package com.crypto.lookup.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.crypto.lookup.data.listeners.onGetDataListListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Date
import kotlin.streams.toList

class SignalCoinFirebaseDaoImpl : SignalCoinDao {
    private val db = Firebase.firestore.collection("signals")
    private val batch = Firebase.firestore.batch()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun retrieve(coinList: SignalCoinList, listener: onGetDataListListener) {
        db.whereIn("symbol",coinList.signalCoins.stream().map{it.symbol}.toList())
            .whereGreaterThan("openDate",Date(System.currentTimeMillis()-86400000)).get().addOnSuccessListener {
                listener.onSuccess(it.documents)
            }.addOnFailureListener{
                listener.onFailed(it)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun fakeData(){
        val openDate = Date(System.currentTimeMillis())
        arrayListOf<SignalCoin>(
            SignalCoin("BTCUSDT",0F,null, openDate,null),
            SignalCoin("ETHUSDT",0F,null, openDate,null),
            SignalCoin("BNBUSDT",0F,null, openDate,null),
            SignalCoin("SOLUSDT",0F,null, openDate,null),
        ).forEach{
            batch.set(db.document(),it)
        }
        batch.commit()
    }
}