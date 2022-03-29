package com.crypto.lookup.data

import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.data.listeners.onSaveDataListener

interface UserDao {
    fun save(user: User, listener: onSaveDataListener)
    fun retrieve(email: String, listener: onGetDataListener)
}