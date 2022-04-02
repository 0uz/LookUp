package com.crypto.lookup.data

import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.google.firebase.auth.FirebaseUser

class UserService(private val userDao: UserDao) {

    fun save(user: User, password: String, listener: onSaveDataListener) {
        return userDao.save(user, password, listener)
    }

    fun retrieve(email: String, listener: onGetDataListener) {
        return userDao.retrieve(email, listener)
    }


    fun loginAuth(email: String, password: String, listener: onSaveDataListener) {
        return userDao.loginAuth(email, password, listener)
    }

    fun currentUser(): FirebaseUser? {
        return userDao.currentUser()
    }
}