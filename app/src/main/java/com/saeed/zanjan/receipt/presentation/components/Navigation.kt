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
import com.saeed.zanjan.receipt.presentation.ui.create_receipt.CreateReceiptViewModel
import com.saeed.zanjan.receipt.presentation.ui.editReceipt.EditReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.editReceipt.EditReceiptViewModel
import com.saeed.zanjan.receipt.presentation.ui.home.Home
import com.saeed.zanjan.receipt.presentation.ui.home.HomeViewModel
import com.saeed.zanjan.receipt.presentation.ui.receipt.ReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.receipt.ReceiptViewModel
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
    val createReceiptViewModel:CreateReceiptViewModel= viewModel()
    val editReceiptViewModel:EditReceiptViewModel= viewModel()
    val receiptViewMode:ReceiptViewModel= viewModel()
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
                },
                navigateToCreateReceiptScreen = {
                    navController.navigate(Screen.CreateReceipt.route)

                }
            )
        }

        composable(route = Screen.Receipt.route + "/{receiptId}",
            arguments = listOf(
                navArgument("receiptId") { type = NavType.IntType }
            )) {navBackStackEntry->
            ReceiptScreen(
                viewModel=receiptViewMode,
                receiptId =navBackStackEntry.arguments?.getInt("receiptId") ,
                navController=navController,
                onNavigateToEdit = {
                    navController.navigate(it)
                }
            )
        }
        composable(Screen.CreateReceipt.route) {
                navBackStackEntry->
            CreateReceiptScreen(
               navController=navController,
                viewModel=createReceiptViewModel,
                navigateToEditReceiptScreen={
                    navController.navigate(it)

                }
                )
        }

        composable(Screen.EditReceipt.route+ "/{receiptId}",
            arguments = listOf(
                navArgument("receiptId") { type = NavType.IntType },
            )) {
                navBackStackEntry->
            EditReceiptScreen(
                navController=navController,
                viewModel=editReceiptViewModel,
                receiptId =navBackStackEntry.arguments?.getInt("receiptId")!!,

            )
        }
    }
}