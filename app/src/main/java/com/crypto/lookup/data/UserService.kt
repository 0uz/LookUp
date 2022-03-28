package com.crypto.lookup.data

class UserService(private val userDao: UserDao) {

    fun save(user: User): Long {
        return userDao.save(user)
    }

    fun retrieve(email: String, listener: onGetDataListener) {
        return userDao.retrieve(email, listener)
    }
}