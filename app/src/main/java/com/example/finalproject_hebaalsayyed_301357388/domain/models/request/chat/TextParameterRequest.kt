package com.example.finalproject_hebaalsayyed_301357388.domain.models.request.chat

import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.Message
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TextParameterRequest(
    @Json(name = "model")
    val model: String = "gpt-3.5-turbo",
    @Json(name = "messages")
    val messages: List<Message>
)

