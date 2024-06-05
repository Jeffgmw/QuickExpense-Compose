package com.example.quickexpense_compose.utils

sealed class Screens(val route: String) {
    object Dashboard : Screens(route = "dashboard")
    object TransactionDetails : Screens(route = "transaction_details")
    object AddEditTransaction : Screens(route = "add_transaction")
    object About : Screens(route = "about")
    object Transactions : Screens("transactions")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
