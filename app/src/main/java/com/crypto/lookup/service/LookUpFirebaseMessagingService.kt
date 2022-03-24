package com.crypto.lookup.service

import android.util.Log
import android.widget.Toast
import com.crypto.lookup.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.ktx.messaging


class LookUpFirebaseMessagingService : FirebaseMessagingService() {

    val db = Firebase.firestore

    override fun onNewToken(token: String) {

        super.onNewToken(token)
    }

    fun registerToken () {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            val coins : List<String> = listOf("BTCUSDT","ETHUSDT")

            val user = hashMapOf(
                "selectedCoins" to coins
            )

            db.collection("tokens")
                .document(token)
                .set(user)
                .addOnSuccessListener { documentReferance ->
                    Log.d("KEY:", token)
                    Log.d("Success","Document added")
                }.addOnFailureListener { e->
                    Log.w("Error","Error ading document",e)
                }


        })

        Firebase.messaging.isAutoInitEnabled=true
    }


}