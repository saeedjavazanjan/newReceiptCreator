package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.presentation.ui.profile_edit.ProfileEditScreen
import com.saeed.zanjan.receipt.presentation.ui.create_receipt.CreateReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.create_receipt.CreateReceiptViewModel
import com.saeed.zanjan.receipt.presentation.ui.customers_list.CustomersListScreen
import com.saeed.zanjan.receipt.presentation.ui.customers_list.CustomersListViewModel
import com.saeed.zanjan.receipt.presentation.ui.editReceipt.EditReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.editReceipt.EditReceiptViewModel
import com.saeed.zanjan.receipt.presentation.ui.home.Home
import com.saeed.zanjan.receipt.presentation.ui.home.HomeViewModel
import com.saeed.zanjan.receipt.presentation.ui.profile_edit.ProfileEditViewModel
import com.saeed.zanjan.receipt.presentation.ui.receipt.ReceiptScreen
import com.saeed.zanjan.receipt.presentation.ui.receipt.ReceiptViewModel
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationViewModel
import com.saeed.zanjan.receipt.presentation.ui.splash.SplashScreen
import com.saeed.zanjan.receipt.presentation.ui.splash.SplashViewModel

@Composable
fun Navigation(
    navController: NavHostController
    /* appDataStore: AppDataStore,
     connectivityManager: ConnectivityManager,
     scaffoldState:ScaffoldState,*/
) {
    val registrationViewModel: RegistrationViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val createReceiptViewModel: CreateReceiptViewModel = viewModel()
    val editReceiptViewModel: EditReceiptViewModel = viewModel()
    val receiptViewMode: ReceiptViewModel = viewModel()
    val splashViewModel: SplashViewModel = viewModel()
    val profileEditViewModel: ProfileEditViewModel = viewModel()
    val customersListViewModel: CustomersListViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Splash.route) {
            SplashScreen(
                viewModel = splashViewModel,
                navigateToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }


        composable(Screen.Registration.route) {
            RegistrationScreen(
                viewModel = registrationViewModel,
                navigateToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(Screen.Home.route) {
            Home(
                viewModel = homeViewModel,
                navigateToReceiptScreen = {
                    navController.navigate(it)
                },
                navigateToCreateReceiptScreen = {
                    navController.navigate(Screen.CreateReceipt.route)

                },
                navigateToProfileSetting = {

                    navController.navigate(Screen.ProfileEdit.route)
                } ,
                navigateToAboutUs = {},
                navigateToCustomersList = {
                    navController.navigate(Screen.CustomersList.route)
                }

            )
        }
        composable(Screen.ProfileEdit.route) {
            ProfileEditScreen(profileEditViewModel ,navController)
        }
        composable(route = Screen.Receipt.route + "/{receiptId}/{newSaved}/{newUpdate}/{statusChanged}/{paymentChanged}",
            arguments = listOf(
                navArgument("receiptId") { type = NavType.IntType },
                navArgument("newSaved") { type = NavType.BoolType },
                navArgument("newUpdate") { type = NavType.BoolType },
                navArgument("statusChanged") { type = NavType.BoolType },
                navArgument("paymentChanged") { type = NavType.BoolType }
            )) { navBackStackEntry ->
            ReceiptScreen(
                viewModel = receiptViewMode,
                receiptId = navBackStackEntry.arguments?.getInt("receiptId"),
                navController = navController,
                newSaved = navBackStackEntry.arguments?.getBoolean("newSaved")!!,
                newUpdate = navBackStackEntry.arguments?.getBoolean("newUpdate")!!,
                statusChanged = navBackStackEntry.arguments?.getBoolean("statusChanged")!!,
                paymentChanged = navBackStackEntry.arguments?.getBoolean("paymentChanged")!!,

                )
        }
        composable(Screen.CreateReceipt.route) {
            CreateReceiptScreen(
                navController = navController,
                viewModel = createReceiptViewModel,

                )
        }
        composable(Screen.CustomersList.route) {
            CustomersListScreen(
                viewModel = customersListViewModel,
                navController=navController
                )
        }

        composable(
            Screen.EditReceipt.route + "/{receiptId}",
            arguments = listOf(
                navArgument("receiptId") { type = NavType.IntType },
            )
        ) { navBackStackEntry ->
            EditReceiptScreen(
                navController = navController,
                viewModel = editReceiptViewModel,
                receiptId = navBackStackEntry.arguments?.getInt("receiptId")!!,

                )
        }
    }
}