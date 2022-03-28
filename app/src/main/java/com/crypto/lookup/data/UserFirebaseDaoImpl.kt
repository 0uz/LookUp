package com.crypto.lookup.data

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirebaseDaoImpl : UserDao {

    private val db = Firebase.firestore

    override fun save(user: User): Long {
        // TODO
        Log.d("DATABASE", "Firebase'e kaydedildi")
        db.collection("users").document(user.email).set(user).addOnSuccessListener {
            return@addOnSuccessListener
        }.addOnFailureListener {
            return@addOnFailureListener
        }
        return 1L;
    }

    override fun retrieve(email: String, listener: onGetDataListener) {
        db.collection("users").document("test").get().addOnSuccessListener {
            listener.onSuccess(it)
        }.addOnFailureListener {
            listener.onFailed(it)
        }
    }
}