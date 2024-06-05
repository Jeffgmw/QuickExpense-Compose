package com.example.quickexpense_compose.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quickexpense_compose.R
import com.example.quickexpense_compose.presentation.about.About
import com.example.quickexpense_compose.presentation.add_edit_transaction.AddEditTransaction
import com.example.quickexpense_compose.presentation.dashboard.Dashboard
import com.example.quickexpense_compose.presentation.transaction_details.TransactionDetails
import com.example.quickexpense_compose.presentation.transactions.Transactions


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetupNavigation() {

    val navController = rememberNavController()
    val transactionId by remember { mutableStateOf(-1) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute == Screens.Dashboard.route || currentRoute == Screens.Transactions.route) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    BottomBar(navController = navController)
                    Column {
                        FloatingActionButton(onClick = {
                            navController.navigate(Screens.AddEditTransaction.withArgs(transactionId.toString(), currentRoute))

                        }, backgroundColor = Color(0xFF243D25)) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "Add Transactions",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        },

        ) {
        NavigationGraph(
            navController = navController,
        )
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.Dashboard.route) {

        composable(
            route = Screens.Dashboard.route,
        ) {
            Dashboard(navController)
        }

        composable(
            route = Screens.AddEditTransaction.route + "/{id}/{previousScreen}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "previousScreen"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val transactionId = it.arguments?.getInt("id") ?: -1
            val previousScreen = it.arguments?.getString("previousScreen") ?: ""

            AddEditTransaction(
                navHostController = navController,
                transactionId = transactionId,
                previousScreen = previousScreen
            )
        }

        composable(
            route = Screens.TransactionDetails.route + "/{transactionId}",
            arguments = listOf(
                navArgument(
                    name = "transactionId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                })
        ) {
            val transactionId = it.arguments?.getInt("transactionId") ?: -1

            TransactionDetails(navHostController = navController, transactionId = transactionId)
        }

        composable(route = Screens.About.route) {
            About()
        }

        composable(
            route = Screens.Transactions.route
        ) {
            Transactions(navHostController = navController)
        }

    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.grey)

    ) {
        BottomNavigationItem(
            icon =
            {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.dashboard),
                )
            },
            label = {
                Text(text = "Dashboard")
            },


            selectedContentColor = Color(0xFF243D25),
            unselectedContentColor = Color.White,
            alwaysShowLabel = false,
            selected = currentRoute == Screens.Dashboard.route,

            onClick = {
                navController.navigate(Screens.Dashboard.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        )

        Row() {
            Spacer(modifier = Modifier.width(56.dp))
        }

        BottomNavigationItem(
            icon =
            {
                Icon(
                    imageVector = Icons.Filled.Assignment,
                    contentDescription = stringResource(R.string.transactions),
                )
            },
            label = {
                Text(text = "Transactions")
            },

            selectedContentColor = Color(0xFF243D25),
            unselectedContentColor = Color.White,
            alwaysShowLabel = false,
            selected = currentRoute == Screens.Transactions.route,
            onClick = {
                navController.navigate(Screens.Transactions.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        )
    }
}

