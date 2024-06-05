package com.example.quickexpense_compose.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val amount: Long,
    val transactionType: String,
    val tags: String,
    val date: String,
    val note: String
)
