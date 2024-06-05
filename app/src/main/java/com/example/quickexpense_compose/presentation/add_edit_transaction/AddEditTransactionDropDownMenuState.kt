package com.example.quickexpense_compose.presentation.add_edit_transaction

data class AddEditTransactionDropDownMenuState(
    val isExpanded: Boolean = false,
    val selectedOption: String = "",
    val hint: String = ""
)
