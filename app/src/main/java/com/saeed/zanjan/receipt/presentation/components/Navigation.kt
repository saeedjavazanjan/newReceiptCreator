package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.presentation.ui.home.Home
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
            Home()
        }

    }
}