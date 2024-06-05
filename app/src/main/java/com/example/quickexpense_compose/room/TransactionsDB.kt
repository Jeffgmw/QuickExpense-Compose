package com.example.quickexpense_compose.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quickexpense_compose.domain.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class TransactionsDB : RoomDatabase() {
    abstract val transactionDao: TransactionDao

    companion object {
        const val DATABASE_NAME = "transactions_db"
    }
}