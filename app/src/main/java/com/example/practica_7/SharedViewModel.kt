package com.example.practica_7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practica_7.models.Users

class SharedViewModel : ViewModel() {
    private val _selectedUser = MutableLiveData<Users?>() //clase generica que puede contener cualquier tipo de dato
    val selectedUser: LiveData<Users?> get() = _selectedUser

    fun setUser(user: Users?) {
        _selectedUser.value = user
    }

    fun clearUser() {
        _selectedUser.value = null
    }
}