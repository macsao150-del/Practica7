package com.example.practica_7

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.practica_7.models.Users
import com.example.practica_7.repositories.SupabaseProvider
import com.example.practica_7.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class supaBaseActivity1 : AppCompatActivity() {
    private lateinit var txtResultado: TextView
    private lateinit var btnCargarUsuarios: Button
    private lateinit var btnAgregarUsuarios: Button
    private lateinit var btnEliminarUsuarios: Button
    private lateinit var btnActualizarUsuarios: Button
    private lateinit var btnSeleccionarUsuario: Button
    private lateinit var progressBar: ProgressBar
    private val userRepo = UserRepository(SupabaseProvider.client)
    private val storeViewModel: SharedViewModel by lazy {
        AppViewModelStore.provider.get(SharedViewModel::class.java)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supa_base1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtResultado = findViewById(R.id.txtResultado)
        btnCargarUsuarios = findViewById(R.id.btnCargarUsuarios)
        progressBar = findViewById(R.id.progressBar)
        btnAgregarUsuarios = findViewById(R.id.btnAgregarUsuarios)
        btnActualizarUsuarios = findViewById(R.id.btnActualizarUsuarios)
        btnEliminarUsuarios = findViewById(R.id.btnEliminarUsuarios)
        btnSeleccionarUsuario = findViewById(R.id.btnSeleccionarUsuario)




        btnCargarUsuarios.setOnClickListener {
            loadUsers()
        }
        btnAgregarUsuarios.setOnClickListener {
            val newUser = Users(
                user = "Briseida",
                email = "briseida@briseida.com",
                password = "123456"
            )
            addUser(newUser)
        }
        btnEliminarUsuarios.setOnClickListener {
            deleteUser(7)
        }
        btnActualizarUsuarios.setOnClickListener {
            val usuario = Users(
                id = 8,
                user = "Bri",
                email = "briseida@briseida.com",
                password = "123456"
            )
            updateUser(usuario)
        }
        btnSeleccionarUsuario.setOnClickListener{
            val user = Users(
                id = 8,
                user = "Briseida",
                email = "briseida@briseida.com",
                password = "123456"
            )
            storeViewModel.setUser(user)
            Toast.makeText(this, "Usuario Seleccionado", Toast.LENGTH_SHORT).show()
        }


    }


    private fun loadUsers() {
        progressBar.visibility = View.VISIBLE
        btnCargarUsuarios.isEnabled = false
        txtResultado.text=""

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = userRepo.getUsers()
                val resultText = user.joinToString("\n") {
                    "${it.user} (${it.email}, (${it.password})"
                }
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnCargarUsuarios.isEnabled = true
                    txtResultado.text = resultText
                }

            } catch (e: Exception) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnCargarUsuarios.isEnabled = true
                    txtResultado.text = "Error: ${e.localizedMessage}"
                }
            }
        }
    }
    private fun addUser(newUsers: Users) {
        progressBar.visibility = View.VISIBLE
        btnAgregarUsuarios.isEnabled = false
        txtResultado.text = "Agregando usuario..."
        //BLOQUEA EL BOTON Y MIENTRAS MUESTRA EL TEXTO CARGANDO USUARIO

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = userRepo.addUser(newUsers)

                runOnUiThread {
                    txtResultado.text = "Usuario agregado con éxito"
                }

            } catch (e: Exception) {
                runOnUiThread {
                    txtResultado.text = "Error: ${e.localizedMessage}"
                }

            } finally {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnAgregarUsuarios.isEnabled = true
                }
            }
        }

    }
    private fun deleteUser(userId: Long) {
        progressBar.visibility = View.VISIBLE
        btnEliminarUsuarios.isEnabled = false
        txtResultado.text = "Eliminando usuario..."

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = userRepo.deleteUser(userId)
                runOnUiThread {
                    txtResultado.text = "Usuario eliminado con éxito"
                }
            }catch (e: Exception) {
                runOnUiThread {
                    txtResultado.text = "Error: ${e.localizedMessage}"
                }

            } finally {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnEliminarUsuarios.isEnabled = true
                }
            }
        }
    }
    private fun updateUser(users: Users) {
        progressBar.visibility = View.VISIBLE
        btnActualizarUsuarios.isEnabled = false
        txtResultado.text = "Actualizando usuario..."

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = userRepo.updateUser(users)
                runOnUiThread { //esto es para que se ejecute en el hilo principal, es decir, en el hilo de la interfaz de usuario. Esto es una buena práctica para evitar errores de concurrencia.
                    txtResultado.text = "Usuario actualizado con éxito"
                }
            } catch (e: Exception) {
                runOnUiThread {
                    txtResultado.text = "Error: ${e.localizedMessage}"
                }
            } finally {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnActualizarUsuarios.isEnabled = true
                    txtResultado.text = ""
                }
            }


        }
    }
}