package com.example.practica_7.models

import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val id: Long? = null,
    val user: String,
    val password: String,
    val created_at: String? = null,
    val email: String
)