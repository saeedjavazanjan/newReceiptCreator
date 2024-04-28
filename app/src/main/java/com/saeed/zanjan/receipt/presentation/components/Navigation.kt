package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saeed.zanjan.receipt.presentation.ui.registration.OtpScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationScreen

@Composable
fun Navigation(
    navController: NavHostController
    /* appDataStore: AppDataStore,
     connectivityManager: ConnectivityManager,
     scaffoldState:ScaffoldState,*/
) {
    NavHost(navController = navController, startDestination = "registration") {
        composable("registration") {
            RegistrationScreen(
                onSignInClicked =  navController::navigate,
                onSignUpClicked = {
                },

            )
        }
        composable("otpScreen") {
           OtpScreen(
               onSendOtpClicked = {

           })
        }
    }
}