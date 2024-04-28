package com.saeed.zanjan.receipt.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object Otp : Screen("otpScreen")

}