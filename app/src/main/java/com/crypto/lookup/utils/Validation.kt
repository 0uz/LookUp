package com.crypto.lookup.utils

import android.util.Patterns

class Validation {
    companion object {
        fun isEmailValid(email: String): Boolean {
            return if (email.isNotBlank() || email.isNullOrEmpty()) false
            else Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}