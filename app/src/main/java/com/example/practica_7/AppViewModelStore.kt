package com.example.practica_7

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object AppViewModelStore : ViewModelStoreOwner {
    private val appViewModelStore = ViewModelStore()
    override val viewModelStore: ViewModelStore
        get() = appViewModelStore

    val provider: ViewModelProvider by lazy { ViewModelProvider(this)
    }
}