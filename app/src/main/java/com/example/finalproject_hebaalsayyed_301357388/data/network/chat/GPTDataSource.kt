package com.example.finalproject_hebaalsayyed_301357388.data.network.chat

import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.GPTResponse
import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.chat.TextParameterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface GPTDataSource {

    @POST("chat/completions")
    suspend fun getAnswer(@Body parameterRequest: TextParameterRequest): GPTResponse

}