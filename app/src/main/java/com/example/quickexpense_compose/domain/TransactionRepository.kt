package com.example.quickexpense_compose.domain

import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insert(transaction: Transaction)
    suspend fun update(transaction: Transaction)
    suspend fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun getTransactionById(id:Int): Flow<Transaction>
    suspend fun deleteTransactionById(id:Int)

}