package com.crypto.lookup.data

import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.google.firebase.auth.FirebaseUser

interface UserDao {
    fun save(user: User, listener: onSaveDataListener)
    fun retrieve(email: String, listener: onGetDataListener)
    fun createAuth(user: User, password: String, listener: onSaveDataListener)
    fun loginAuth(email: String, password: String, listener: onSaveDataListener)
    fun currentUser(): FirebaseUser?
}