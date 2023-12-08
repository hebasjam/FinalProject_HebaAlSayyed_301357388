package com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GPTResponse (
    @Json(name = "choices")
    val choiceResponses: List<ChoiceResponse>,
    @Json(name = "created")
    val created: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "object")
    val objectX: String,
    @Json(name = "usage")
    val usage: UsageModel
    )