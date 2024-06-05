package com.example.quickexpense_compose.presentation.transactions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickexpense_compose.domain.Transaction
import com.example.quickexpense_compose.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val userDataRepository: TransactionRepository
) : ViewModel() {

    private val _transactions = mutableStateOf(TransactionsState())
    val transactions: State<TransactionsState> = _transactions

    private val _transactionType = mutableStateOf(
        TransactionsDropDownMenuState(selectedOption = "All")
    )
    val transactionType: State<TransactionsDropDownMenuState> = _transactionType

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            userDataRepository.getAllTransactions().collect { transactions ->
                updateTransactions(transactions)
            }
        }
    }

    private fun updateTransactions(transactions: List<Transaction>) {
        val selectedOption = _transactionType.value.selectedOption
        val filteredTransactions = when (selectedOption) {
            "Expense" -> transactions.filter { it.transactionType == "Expense" }
            "Income" -> transactions.filter { it.transactionType == "Income" }
            else -> transactions
        }
        _transactions.value = _transactions.value.copy(list = filteredTransactions.reversed())
    }

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.OnExpandedChange -> {
                _transactionType.value = _transactionType.value.copy(isExpanded = !_transactionType.value.isExpanded)
            }
            is TransactionsEvent.OnDismissRequest -> {
                _transactionType.value = _transactionType.value.copy(isExpanded = false)
            }
            is TransactionsEvent.ChangeSelectedOption -> {
                _transactionType.value = _transactionType.value.copy(selectedOption = event.value)
                loadTransactions()
            }
        }
    }
}
