package com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChoiceResponse(
    @Json(name = "message")
    val message: Message? = null,
    var searchTime: Long = 0, )
