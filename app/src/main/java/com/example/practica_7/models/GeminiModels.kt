package com.example.practica_7.models

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class GeminiRequest(
    val contents: List<GeminiContent>
)

@Serializable
data class GeminiContent(
    val parts: List<GeminiPart>
)

@Serializable
data class GeminiPart(
    val text: String
)

@Serializable //Respuesta,si hay error lo cambia a null
data class GeminiResponse(
    val candidates: List<GeminiCandidate>? = null
)

@Serializable //Respuesta, si hay error lo cambia a null
data class GeminiCandidate(
    val content: GeminiContent? = null
)