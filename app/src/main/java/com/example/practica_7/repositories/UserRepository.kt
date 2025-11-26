package com.example.practica_7.repositories

import com.example.practica_7.models.Users
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class UserRepository(private val supabase: SupabaseClient) {

    suspend fun getUsers() = supabase.postgrest["Users"] //esto es para que se ejecute en el hilo principal, es decir, en el hilo de la interfaz de usuario
        .select()
        .decodeList<Users>()

    suspend fun addUser(user: Users) = supabase.postgrest["Users"].insert(user)

    suspend fun updateUser(user: Users) = supabase.postgrest["Users"].update(user){
        filter{
            eq("id", user.id!!)

        }
    }

    suspend fun deleteUser(userId: Long) = supabase.postgrest["Users"].delete {
        filter {
            eq("id", userId)
        }
    }
}




