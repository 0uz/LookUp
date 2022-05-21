package com.crypto.lookup.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.crypto.lookup.data.listeners.onGetDataListListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.streams.toList

class SignalCoinFirebaseDaoImpl : SignalCoinDao {
    private val db = Firebase.firestore.collection("signals")
    private val batch = Firebase.firestore.batch()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun retrieve(signalCoin: ArrayList<String>, listener: onGetDataListListener) {
        if (signalCoin.size < 1) {
            listener.onSuccess(listOf())
            return
        }

        if (signalCoin.size < 10) {
            subCoinLoop(signalCoin, listener)
            return
        }

        var counter = signalCoin.size / 10
        if (signalCoin.size % 10 != 0) counter++
        val sumSignalCoin: ArrayList<DocumentSnapshot> = arrayListOf()
        val subListener = object : onGetDataListListener {
            override fun onSuccess(data: List<DocumentSnapshot>) {
                counter--
                if (counter == 0) {
                    listener.onSuccess(sumSignalCoin)
                    return
                }
                sumSignalCoin.addAll(data)
            }

            override fun onFailed(e: Exception) {
                listener.onFailed(e)
            }

        }

        for (x in 0..signalCoin.size step 10) {
            if (signalCoin.size == x) return
            if (x + 10 > signalCoin.size)
                subCoinLoop(signalCoin.subList(x, signalCoin.size).stream().toList(), subListener)
            else
                subCoinLoop(signalCoin.subList(x, x + 10).stream().toList(), subListener)

        }


    }

    private fun subCoinLoop(signalCoin: List<String>, listener: onGetDataListListener) {
//        val currentTimeMillis = System.currentTimeMillis()
        db.whereIn("symbol", signalCoin)
            .orderBy("closeDate", Query.Direction.DESCENDING)
//            .whereGreaterThanOrEqualTo("openDate", Date(currentTimeMillis - 86400000))
//            .whereLessThanOrEqualTo("openDate", Date(currentTimeMillis))
//            .whereEqualTo("isOpen", true)
            //TODO null olmayanlari cekme kismini ekle
            //TODO 10dan fazla kullanici ekleme durumu handle et
            .get().addOnSuccessListener {
                listener.onSuccess(it.documents)
            }.addOnFailureListener {
                listener.onFailed(it)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun fakeData() { // TODO DELETE
        val currentTimeMillis = System.currentTimeMillis()
//        arrayListOf<SignalCoin>(
//            SignalCoin("BTCUSDT", 0F, null, java.util.Date(currentTimeMillis.minus(86400000 * 2)), null),
//            SignalCoin("ETHUSDT", 0F, null, java.util.Date(currentTimeMillis), null),
//            SignalCoin("BNBUSDT", 0F, null, java.util.Date(currentTimeMillis.minus(86400000 * 2)), null),
//            SignalCoin("SOLUSDT", 0F, null, java.util.Date(currentTimeMillis), null),
//        ).forEach {
//            batch.set(db.document(), it)
//        }
        batch.commit()
    }
}