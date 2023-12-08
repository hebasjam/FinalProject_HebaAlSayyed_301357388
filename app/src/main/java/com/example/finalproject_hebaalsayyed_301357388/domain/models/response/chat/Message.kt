package com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "role")
    val role: String,
    @Json(name = "content")
    val content: String
)
