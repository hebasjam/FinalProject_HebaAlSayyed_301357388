package com.example.finalproject_hebaalsayyed_301357388.domain.repositories.chat

import com.example.finalproject_hebaalsayyed_301357388.data.network.chat.GPTDataSource
import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.Message
import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.chat.TextParameterRequest
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val gptDataSource: GPTDataSource
) {
    suspend fun getAnswer(messages:List<Message>): String {
        val request = TextParameterRequest(messages = messages)
        val response = gptDataSource.getAnswer(request)
        return response.choiceResponses.last().message?.content ?: ""
    }
}
