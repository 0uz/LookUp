package com.crypto.lookup.data

import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.google.firebase.auth.FirebaseUser

class UserService(private val userDao: UserDao) {

    fun save(user: User, listener: onSaveDataListener) {
        return userDao.save(user, listener)
    }

    fun retrieve(email: String, listener: onGetDataListener) {
        return userDao.retrieve(email, listener)
    }

    fun createAuth(user: User, password: String, listener: onSaveDataListener) {
        return userDao.createAuth(user, password, listener)
    }

    fun loginAuth(email: String, password: String, listener: onSaveDataListener) {
        return userDao.loginAuth(email, password, listener)
    }

    fun currentUser(): FirebaseUser? {
        return userDao.currentUser()
    }
}