package com.crypto.lookup.data

interface UserDao {
    fun save(user: User): Long
    fun retrieve(email: String, listener: onGetDataListener)
}