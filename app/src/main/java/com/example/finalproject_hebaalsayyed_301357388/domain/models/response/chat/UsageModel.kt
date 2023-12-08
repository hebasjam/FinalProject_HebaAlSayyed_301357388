package com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsageModel(
    @Json(name = "completion_tokens")
    val completionTokens: Int,
    @Json(name = "prompt_tokens")
    val promptTokens: Int,
    @Json(name = "total_tokens")
    val totalTokens: Int
)
