package com.crypto.lookup.data

import java.util.*

data class Fear(
    var index: Int,
    var classification: String,
)

data class Tweet(
    var symbol: String = "",
    @field:JvmField
    var tweet_count: Int = 0,
    var start: Date? = null,
    var end: Date? = null
)