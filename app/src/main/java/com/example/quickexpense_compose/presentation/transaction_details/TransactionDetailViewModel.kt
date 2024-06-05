package com.example.quickexpense_compose.presentation.transaction_details

import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickexpense_compose.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val userDataRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionId: Int = checkNotNull(savedStateHandle["transactionId"])
    private val _currTransaction = mutableStateOf(CurrTransactionState())
    val currTransaction: State<CurrTransactionState> = _currTransaction

    init {
        loadTransactionDetails()
    }

    private fun loadTransactionDetails() {
        viewModelScope.launch {
            userDataRepository.getTransactionById(transactionId).collect { transaction ->
                _currTransaction.value = CurrTransactionState(transaction = transaction)
            }
        }
    }

    fun onEvent(event: TransactionsDetailEvent) {
        when (event) {
            is TransactionsDetailEvent.Delete -> handleDeleteEvent(event)
            is TransactionsDetailEvent.Edit -> handleEditEvent(event)
            is TransactionsDetailEvent.Share -> handleShareEvent(event)
        }
    }

    private fun handleDeleteEvent(event: TransactionsDetailEvent.Delete) {
        event.navHostController.navigateUp()
        viewModelScope.launch {
            userDataRepository.deleteTransactionById(event.id)
        }
    }

    private fun handleEditEvent(event: TransactionsDetailEvent.Edit) {
        // Handle the edit event here
    }

    private fun handleShareEvent(event: TransactionsDetailEvent.Share) {
        val transaction = _currTransaction.value.transaction
        val shareText = transaction?.let {
            if (it.transactionType == "Expense") {
                "I paid KES ${it.amount} for ${it.title}."
            } else {
                "I earned KES ${it.amount} from ${it.title}."
            }
        } ?: ""

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        event.context.startActivity(shareIntent)
    }
}
