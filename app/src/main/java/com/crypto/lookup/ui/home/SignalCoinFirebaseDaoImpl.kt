package com.crypto.lookup.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.crypto.lookup.data.listeners.onGetDataListListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Date

class SignalCoinFirebaseDaoImpl : SignalCoinDao {
    private val db = Firebase.firestore.collection("signals")
    private val batch = Firebase.firestore.batch()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun retrieve(signalCoin: ArrayList<String>, listener: onGetDataListListener) {
        val currentTimeMillis = System.currentTimeMillis()
        if (signalCoin.size < 1) {
            listener.onSuccess(listOf())
            return
        }

        db.whereIn("symbol", signalCoin)
            .whereGreaterThanOrEqualTo("openDate", Date(currentTimeMillis - 86400000))
            .whereLessThanOrEqualTo("openDate", Date(currentTimeMillis))
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