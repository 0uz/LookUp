package com.crypto.lookup.utils

import android.util.Patterns

class Validation {
    companion object {
        fun isEmailValid(email: String): Boolean {
            return if (email.isNullOrEmpty()) false
            else Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isTextValid(text: String, max: Int, min: Int): Boolean {
            return !(text.isNullOrEmpty() || text.length > max || text.length < min)
        }
    }
}