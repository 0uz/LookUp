package com.crypto.lookup.data

import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener

class UserService(private val userDao: UserDao) {

    fun save(user: User, listener: onSaveDataListener) {
        return userDao.save(user, listener)
    }

    fun retrieve(email: String, listener: onGetDataListener) {
        return userDao.retrieve(email, listener)
    }
}