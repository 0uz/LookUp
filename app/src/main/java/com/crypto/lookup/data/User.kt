package com.crypto.lookup.data

import java.io.Serializable
import java.util.*

data class User(
    val name: String = "",
    val surname: String = "",
    val identityNumber: Long = 0L,
    val phoneNumber: Long = 0L,
    val birthDate: Date? = null,
    val email: String = "",
    var phoneID: String = "",
    val subscribedCoins: ArrayList<String> = arrayListOf()
) : Serializable
