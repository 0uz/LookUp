package com.crypto.lookup.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crypto.lookup.data.User

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<User>(User())
    val user: LiveData<User> = _user

    fun setCurrentUser(user: User) {
        _user.value = user
        _user.postValue(user)
    }

    fun getCurrentUser(): User {
        return _user.value!!
    }
}