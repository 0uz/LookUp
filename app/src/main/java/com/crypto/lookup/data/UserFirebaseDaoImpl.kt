package com.crypto.lookup.data

import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirebaseDaoImpl : UserDao {

    private val db = Firebase.firestore

    override fun save(user: User, listener: onSaveDataListener) {
        db.collection("users").document(user.email).set(user).addOnSuccessListener {
            listener.onSuccess()
        }.addOnFailureListener {
            listener.onFailed()
        }
    }

    override fun retrieve(email: String, listener: onGetDataListener) {
        db.collection("users").document("test").get().addOnSuccessListener {
            listener.onSuccess(it)
        }.addOnFailureListener {
            listener.onFailed(it)
        }
    }
}