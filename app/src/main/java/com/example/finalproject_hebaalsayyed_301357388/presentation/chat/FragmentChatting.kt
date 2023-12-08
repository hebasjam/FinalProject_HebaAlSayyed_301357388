package com.example.finalproject_hebaalsayyed_301357388.presentation.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.finalproject_hebaalsayyed_301357388.R
import com.example.finalproject_hebaalsayyed_301357388.databinding.FragmentChatBinding
import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.Message
import com.example.finalproject_hebaalsayyed_301357388.presentation.chat.adapter.QuestionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentChatting : Fragment() {

    private val viewModel: ChattingViewModel by viewModels()
    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: QuestionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMessagesFromDB()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        adapter = QuestionsAdapter()
        binding.recyclerView.adapter = adapter
        binding.btnSend.setOnClickListener { actionListener() }
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

    private fun actionListener() {
        val text = binding.etMessage.text.toString()
        if (text.isNotBlank()) {
            val newUserMessage = Message("user", text)

            lifecycleScope.launch {
                viewModel.addMessageAndDB(newUserMessage)
                // wait for 0.3 seconds
                delay(300)
                viewModel.requestGptResponseFromDB()
            }

            clearChatBox()
        } else {
            Toast.makeText(requireContext(), "Write Something", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearChatBox() {
        binding.etMessage.setText("")
    }

    private fun onSuccess(text: String?) {
        text?.let {
            val newAssistantMessage = Message("assistant", it)
            lifecycleScope.launch {
                viewModel.addMessageAndDB(newAssistantMessage) // This adds and saves the message
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_clear) {
            clearConversation()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun clearConversation() {
        lifecycleScope.launch {
            viewModel.clearMessagesFromDB()
        }
        adapter.submitList(listOf())
        binding.welcomeText.isVisible = true
    }
}
