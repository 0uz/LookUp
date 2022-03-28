package com.crypto.lookup.data

import com.google.firebase.firestore.DocumentSnapshot

interface onGetDataListener {
    fun onSuccess(data: DocumentSnapshot)
    fun onFailed(e: Exception)
}