package com.example.finalproject_hebaalsayyed_301357388.presentation.places.chatting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.finalproject_hebaalsayyed_301357388.databinding.FragmentChatBinding
import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.Message
import com.example.finalproject_hebaalsayyed_301357388.presentation.chat.ChattingViewModel
import com.example.finalproject_hebaalsayyed_301357388.presentation.chat.adapter.QuestionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentPlaceChatting: Fragment() {

    private val viewModel: ChattingViewModel by viewModels() // Use a dedicated ViewModel if needed
    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: QuestionsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()

        val placeName = arguments?.let { FragmentPlaceChattingArgs.fromBundle(it).placeChatting }
        if (placeName != null) {
            startConversationAboutPlace(placeName)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.answerStateFlow.collect { onSuccess(it) }
        }
        lifecycleScope.launch {
            viewModel.answerLoadingStateFlow.collect { onLoading(it) }
        }
        lifecycleScope.launch {
            viewModel.answerErrorStateFlow.collect { onErrorPatients(it) }
        }
        lifecycleScope.launch {
            viewModel.messages.collect {
                adapter.submitList(it)
                if (it.isNotEmpty()) {
                    binding.recyclerView.smoothScrollToPosition(it.size - 1)
                    binding.welcomeText.isVisible = false
                }
            }
        }
    }

    private fun initListeners() {
        adapter = QuestionsAdapter()
        binding.recyclerView.adapter = adapter
        binding.btnSend.setOnClickListener { actionListener() }
    }

    private fun startConversationAboutPlace(placeName: String) {
        val initialMessage = "Tell me more about $placeName"
        sendChatMessage(initialMessage)
    }

    private fun actionListener() {
        val text = binding.etMessage.text.toString()
        if (text.isNotBlank()) {
            sendChatMessage(text)
            clearChatBox()
        } else {
            Toast.makeText(requireContext(), "Write Something", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clearChatBox() {
        binding.etMessage.setText("")
    }

    private fun sendChatMessage(message: String) {
        val newUserMessage = Message("user", message)
        lifecycleScope.launch {
            viewModel.addNewMessage(newUserMessage) // This adds and saves the message
            viewModel.requestGptResponse() // Make the API request with the last 10 messages
        }
    }

    private fun onSuccess(text: String?) {
        text?.let {
            val newAssistantMessage = Message("assistant", it)
            lifecycleScope.launch {
                viewModel.addNewMessage(newAssistantMessage)
            }
        }
    }
    private fun onLoading(show: Boolean) {
        binding.progressCircular.isVisible = show
        binding.btnSend.isClickable = !show
    }

    private fun onErrorPatients(response: Exception?) {
        response?.let {
            Log.d("TAG00", "Error: ${it.message}")
        }
    }
}