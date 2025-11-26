package com.example.practica_7

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class supaBaseActivity2 : AppCompatActivity() {
    private lateinit var txtUsuarioSeleccionado: TextView
    private val storeViewModel: SharedViewModel by lazy {
        AppViewModelStore.provider.get(SharedViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supa_base2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtUsuarioSeleccionado = findViewById(R.id.txtUsuarioSeleccionado)
        storeViewModel.selectedUser.observe(this) { user ->
            if (user != null) {
                txtUsuarioSeleccionado.text = "Usuario Seleccionado: ${user.user}"
            } else {
                txtUsuarioSeleccionado.text = "Ningun usuario seleccionado"
            }
        }

    }
}