package com.example.finalproject_hebaalsayyed_301357388.presentation.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_hebaalsayyed_301357388.R
import com.example.finalproject_hebaalsayyed_301357388.databinding.FragmentOptionsBinding
import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.options.OptionItem
import com.example.finalproject_hebaalsayyed_301357388.presentation.options.adapter.OptionsAdapter

class FragmentOptions: Fragment(),OnOptionClickListener {
    lateinit var binding: FragmentOptionsBinding
    private lateinit var optionsList: List<OptionItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOberver()
    }

    private fun setupOberver() {
        optionsList = listOf(OptionItem("Chat With BOT"), OptionItem("Check Out Places"))

        // Set up RecyclerView
        binding.rvOptions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = OptionsAdapter(optionsList, this@FragmentOptions)
        }

    }

    override fun onOptionClick(optionItem: OptionItem) {
        when (optionItem.text) {
            optionsList[0].text -> findNavController().navigate(R.id.action_optionsFragment_to_fragmentChatting)
            optionsList[1].text  -> findNavController().navigate(R.id.action_optionsFragment_to_fragmentPlacesLocator)
        }
    }
}