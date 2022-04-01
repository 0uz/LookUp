package com.crypto.lookup.data

import java.sql.Timestamp

data class User(
    val name: String = "",
    val surname: String = "",
    val identityNumber: Long = 0L,
    val phoneNumber: Long = 0L,
    val birthDate: Timestamp? = null,
    val email: String = "",
    val phoneID: String = "",
    val selectedCoins: List<String> = emptyList()
)
