package com.example.finalproject_hebaalsayyed_301357388.presentation.options

import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.options.OptionItem

interface OnOptionClickListener {
    fun onOptionClick(optionItem: OptionItem)
}