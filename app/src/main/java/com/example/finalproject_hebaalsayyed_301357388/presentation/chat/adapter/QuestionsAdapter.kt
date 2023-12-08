package com.example.finalproject_hebaalsayyed_301357388.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_hebaalsayyed_301357388.databinding.ItemChatBinding
import com.example.finalproject_hebaalsayyed_301357388.domain.models.response.chat.Message


class QuestionsAdapter : ListAdapter<Message, QuestionsAdapter.ChattingViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingViewHolder {
        val binding = ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ChattingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChattingViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ChattingViewHolder(private val binding:ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            if (message.role == "assistant") {
                binding.rightChatView.isVisible = false
                binding.leftChatView.isVisible = true
                binding.tvLeftChat.text = message.content.trim()
            }
            else{
                binding.leftChatView.isVisible = false
                binding.rightChatView.isVisible = true
                binding.tvRightChat.text = message.content.trim()
            }
        }
    }
    object DIFF_CALLBACK : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            // Your logic here
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.content == newItem.content && oldItem.role == newItem.role
        }
    }

    fun addMessage(message: Message) {
        val updatedList = currentList.toMutableList().apply {
            add(message)
        }
        submitList(updatedList) // Submit the updated list
    }
}