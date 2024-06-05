package com.example.quickexpense_compose.presentation.transaction_details

import com.example.quickexpense_compose.domain.Transaction


data class CurrTransactionState(
    val transaction: Transaction? = Transaction(
        id = -1,
        transactionType = "",
        title = "",
        amount = 0,
        tags = "",
        date = "",
        note = ""
    )

)