package com.example.practica_7

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica_7.repositories.GeminiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Gemini : AppCompatActivity() {
    private lateinit var txtTexto: MultiAutoCompleteTextView
    private lateinit var btnMejorar: Button
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gemini)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtTexto = findViewById(R.id.txtTexto)
        btnMejorar = findViewById(R.id.btnMejorar)
        progressBar = findViewById(R.id.progressBar2)

        btnMejorar.setOnClickListener {
            btnMejorar.isEnabled = false //bloquear el boton para que no le pueda picar varias veces
            txtTexto.isEnabled = false //bloquear el texto para que no se pueda editar
            progressBar.visibility = View.VISIBLE //mostrar el progressbar

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val respuesta = GeminiRepository().askGemini("mejora el siguiente text, solo regresame el texto mejorado no incluyas nada mas: ${txtTexto.text.toString()}")
                    runOnUiThread {
                        txtTexto.setText(respuesta)
                    }


                } catch (e: Exception) {
                    runOnUiThread {
                        txtTexto.setText("Error: ${e.localizedMessage}")
                    }

                } finally {
                    runOnUiThread {
                        txtTexto.isEnabled = true
                        btnMejorar.isEnabled = true
                        progressBar.visibility = View.GONE

                    }
                }
            }
        }
    }
}