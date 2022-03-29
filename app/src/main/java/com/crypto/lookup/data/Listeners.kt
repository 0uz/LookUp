package com.crypto.lookup.data.listeners

import com.google.firebase.firestore.DocumentSnapshot

interface onGetDataListener {
    fun onSuccess(data: DocumentSnapshot)
    fun onFailed(e: Exception)
}

interface onSaveDataListener {
    fun onSuccess()
    fun onFailed()
}