package com.crypto.lookup.data

import android.util.Log
import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class UserFirebaseDaoImpl : UserDao {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val messaging = FirebaseMessaging.getInstance()

    override fun save(user: User, password: String, listener: onSaveDataListener) {
        messaging.token.addOnSuccessListener { phoneID ->
            user.phoneID = phoneID
            db.collection("users").document(user.email).set(user).addOnSuccessListener {
                createAuth(user, password, object : onSaveDataListener {
                    override fun onSuccess() {
                        listener.onSuccess()
                    }

                    override fun onFailed(exception: Exception) {
                        db.collection("users").document(user.email).delete()
                        listener.onFailed(exception)
                    }
                })
            }.addOnFailureListener {
                listener.onFailed(it)
            }
        }.addOnFailureListener {
            listener.onFailed(it)
        }
    }

    private fun createAuth(user: User, password: String, listener: onSaveDataListener) {
        auth.createUserWithEmailAndPassword(user.email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                listener.onSuccess()
            } else {
                Log.w("AUTH", it.exception)
                listener.onFailed(it.exception!!)
            }
        }
    }

    override fun retrieve(email: String, listener: onGetDataListener) {
        db.collection("users").document(email).get().addOnSuccessListener {
            listener.onSuccess(it)
        }.addOnFailureListener {
            listener.onFailed(it)
        }
    }

    override fun loginAuth(email: String, password: String, listener: onSaveDataListener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                listener.onSuccess()
            } else {
                listener.onFailed(it.exception!!)
            }
        }
    }

    override fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }


}