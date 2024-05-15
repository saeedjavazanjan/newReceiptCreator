package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.presentation.ui.create_receipt.CreateReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.home.Home
import com.saeed.zanjan.receipt.presentation.ui.home.HomeViewModel
import com.saeed.zanjan.receipt.presentation.ui.receipt.ReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.OtpScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationViewModel

@Composable
fun Navigation(
    navController: NavHostController
    /* appDataStore: AppDataStore,
     connectivityManager: ConnectivityManager,
     scaffoldState:ScaffoldState,*/
) {
    val registrationViewModel:RegistrationViewModel = viewModel()
    val homeViewModel:HomeViewModel= viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Registration.route) {
            RegistrationScreen(
               viewModel =registrationViewModel,
                navigateToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(Screen.Home.route) {
            Home(
                viewModel=homeViewModel,
                navigateToReceiptScreen = {
                    navController.navigate(it)
                }
            )
        }

        composable(route = Screen.Receipt.route + "/{receiptId}/{navType}/{receiptCategory}",
            arguments = listOf(
                navArgument("receiptId") { type = NavType.IntType },
                navArgument("navType") { type = NavType.StringType },
                navArgument("receiptCategory") { type = NavType.IntType },

            )) {navBackStackEntry->
            ReceiptScreen(
                navType = navBackStackEntry.arguments?.getString("navType") ,
                receiptId =navBackStackEntry.arguments?.getInt("receiptId") ,
                receiptCategory =navBackStackEntry.arguments?.getInt("receiptCategory"),
                navController=navController,
                onNavigateToEdit = {
                    navController.navigate(it)
                }
            )
        }
        composable(Screen.CreateReceipt.route) {
            CreateReceiptScreen(
               navController=navController
            )
        }
    }
}