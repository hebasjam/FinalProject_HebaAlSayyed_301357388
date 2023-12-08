package com.example.finalproject_hebaalsayyed_301357388.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_hebaalsayyed_301357388.data.db.chat.ChatDao
import com.example.finalproject_hebaalsayyed_301357388.domain.repositories.chat.ChatRepository
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.chat.ChatEntity
import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val repoImpl: ChatRepository,
    private val chatDao: ChatDao
) : ViewModel() {

    private val _answerSharedFlow = MutableSharedFlow<String>()
    val answerStateFlow = _answerSharedFlow.asSharedFlow()

    private val _answerLoadingStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val answerLoadingStateFlow = _answerLoadingStateFlow

    private val _answerErrorStateFlow: MutableStateFlow<Exception?> = MutableStateFlow(null)
    val answerErrorStateFlow = _answerErrorStateFlow

    val messages = MutableStateFlow<List<Message>>(listOf())

    // Request a response based on the last 10 messages.
    fun requestGptResponseFromDB() {
        viewModelScope.launch {
            val lastTenFromDb = chatDao.getLastTenMessages()
            val messages = lastTenFromDb.map { Message(it.sender, it.message) }
            _answerLoadingStateFlow.emit(true)
            try {
                _answerSharedFlow.emit(repoImpl.getAnswer(messages))
            } catch (e: Exception) {
                _answerErrorStateFlow.emit(e)
            }
            _answerLoadingStateFlow.emit(false)
        }
    }

    fun requestGptResponse() {
        viewModelScope.launch {
            _answerLoadingStateFlow.emit(true)
            try {
                _answerSharedFlow.emit(repoImpl.getAnswer(messages.value))
            } catch (e: Exception) {
                _answerErrorStateFlow.emit(e)
            }
            _answerLoadingStateFlow.emit(false)
        }
    }

    fun addNewMessage(message: Message) {
        viewModelScope.launch {
            val updatedMessages = messages.value.toMutableList().apply { add(message) }
            messages.emit(updatedMessages)
        }
    }

    fun addMessageAndDB(message: Message) {
        viewModelScope.launch {
            val updatedMessages = messages.value.toMutableList().apply { add(message) }
            messages.emit(updatedMessages)
            chatDao.insertMessage(ChatEntity(sender = message.role, message = message.content))
        }
    }

    fun getMessagesFromDB() {
        viewModelScope.launch {
            val dbMessages = chatDao.getAllMessages()
            val newMessages = dbMessages.map { Message(it.sender, it.message) }
            messages.emit(newMessages)
        }
    }

    fun clearMessagesFromDB() {
        viewModelScope.launch {
            chatDao.clearMessages()
        }
    }
}
