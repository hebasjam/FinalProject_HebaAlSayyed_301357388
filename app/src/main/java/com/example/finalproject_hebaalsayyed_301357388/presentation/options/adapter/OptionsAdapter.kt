package com.example.finalproject_hebaalsayyed_301357388.presentation.options.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_hebaalsayyed_301357388.databinding.ItemOptionBinding
import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.options.OptionItem
import com.example.finalproject_hebaalsayyed_301357388.presentation.options.OnOptionClickListener

class OptionsAdapter(
    private val options: List<OptionItem>,
    private val onOptionClickListener: OnOptionClickListener
    ) : RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.tvOption
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = options[position]
        holder.itemView.setOnClickListener {
            onOptionClickListener.onOptionClick(item)
        }
        holder.textView.text = item.text
    }

    override fun getItemCount() = options.size
}
