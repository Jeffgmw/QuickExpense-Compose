package com.example.quickexpense_compose.presentation.transaction_details

import android.content.Context
import androidx.navigation.NavHostController
import com.example.quickexpense_compose.domain.Transaction

sealed class TransactionsDetailEvent {
    data class Share(val context:Context) : TransactionsDetailEvent()
    data class Edit(val transaction: Transaction) : TransactionsDetailEvent()
    data class Delete(val navHostController: NavHostController, val id: Int) : TransactionsDetailEvent()
}