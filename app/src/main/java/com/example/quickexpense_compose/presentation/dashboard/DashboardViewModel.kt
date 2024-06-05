package com.example.quickexpense_compose.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickexpense_compose.domain.Transaction
import com.example.quickexpense_compose.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userDataRepository: TransactionRepository
) : ViewModel() {

    private val _overviewCardState = mutableStateOf(OverviewCardState())
    val overviewCardState: State<OverviewCardState> = _overviewCardState

    private val _recentTransactions = mutableStateOf(RecentTransactionsListState())
    val recentTransactions: State<RecentTransactionsListState> = _recentTransactions

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            userDataRepository.getAllTransactions().collect { transactions ->
                updateOverviewCardState(transactions)
                updateRecentTransactions(transactions)
            }
        }
    }

    private fun updateOverviewCardState(transactions: List<Transaction>) {
        val incomeTransactions = transactions.filter { it.transactionType == "Income" }
        val expenseTransactions = transactions.filter { it.transactionType == "Expense" }

        val totalIncome = incomeTransactions.sumOf { it.amount }
        val totalExpense = expenseTransactions.sumOf { it.amount }

        val recentIncome = incomeTransactions.takeLast(2).sumOf { it.amount }
        val recentExpense = expenseTransactions.takeLast(2).sumOf { it.amount }

        _overviewCardState.value = OverviewCardState(
            totalBalance = totalIncome - totalExpense,
            income = recentIncome,
            expense = recentExpense
        )
    }

    private fun updateRecentTransactions(transactions: List<Transaction>) {
        _recentTransactions.value = RecentTransactionsListState(
            list = transactions.takeLast(4).reversed()
        )
    }
}
