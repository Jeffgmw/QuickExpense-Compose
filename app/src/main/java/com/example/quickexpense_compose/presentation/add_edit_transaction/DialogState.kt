package com.example.quickexpense_compose.presentation.add_edit_transaction

data class DialogState(
    val state: Boolean = false,
    val text: String ="Do you want to discard this transaction?"
)