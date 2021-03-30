package com.minhnv.c9nvm.dropdownextensions

import com.minhnv.c9nvm.dropdownextension.DropDownTemplate

data class DropDownObj(
    val value: String
): DropDownTemplate {
    override var textDisplay: String = value
    override var isSelected: Boolean = false
}
