package com.example.practica_7.repositories

import com.example.practica_7.models.GeminiContent
import com.example.practica_7.models.GeminiPart
import com.example.practica_7.models.GeminiRequest
import com.example.practica_7.models.GeminiResponse
import com.example.practica_7.providers.HttpProvider
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GeminiRepository {
    private val client = HttpProvider.client
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models"
    private val apikey = "AIzaSyBBkglwB4RbNUo4VSK28ubM0FQMz14fP-Y"

    suspend fun askGemini(prompt: String): String {
        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(parts = listOf(GeminiPart(text = prompt)))
            )
        )

        val response: HttpResponse = client.post("$baseUrl/gemini-2.5-flash:generateContent"){ ///parte final de la url
                contentType(ContentType.Application.Json)
                header("x-goog-api-key", apikey)
                setBody(request)
        }

        val body : GeminiResponse = response.body()

        //?este sirve para que si no hay respuesta, no se pare la app
        return body.candidates?.
                firstOrNull()?.
                content?.parts?.
                firstOrNull()?.text ?: "Sin respuesta"
    }
}