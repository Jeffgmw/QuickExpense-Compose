package com.example.quickexpense_compose.presentation.dashboard

data class OverviewCardState(
    val totalBalance: Long? = 0,
    val income :Long? = 0,
    val expense : Long? = 0
)