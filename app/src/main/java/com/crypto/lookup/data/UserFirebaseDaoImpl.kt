package com.crypto.lookup.data

import android.util.Log
import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirebaseDaoImpl : UserDao {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

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

    override fun createAuth(user: User, password: String, listener: onSaveDataListener) {
        auth.createUserWithEmailAndPassword(user.email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                listener.onSuccess()
            } else {
                Log.w("AUTH", it.exception)
                listener.onFailed()
            }
        }
    }


    override fun loginAuth(email: String, password: String, listener: onSaveDataListener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                listener.onSuccess()
            } else {
                listener.onFailed()
            }
        }
    }

    override fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }


}