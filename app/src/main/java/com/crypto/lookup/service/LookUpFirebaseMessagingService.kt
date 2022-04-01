package com.crypto.lookup.service

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
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

        })

        Firebase.messaging.isAutoInitEnabled=true
    }


}