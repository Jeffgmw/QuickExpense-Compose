package com.example.quickexpense_compose.presentation.dashboard

import com.example.quickexpense_compose.domain.Transaction

data class RecentTransactionsListState(
    val list: List<Transaction> = mutableListOf()
)
