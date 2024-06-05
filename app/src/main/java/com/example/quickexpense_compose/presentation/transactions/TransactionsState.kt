package com.example.quickexpense_compose.presentation.transactions

import com.example.quickexpense_compose.domain.Transaction

data class TransactionsState(
    val list: List<Transaction> = mutableListOf()
)