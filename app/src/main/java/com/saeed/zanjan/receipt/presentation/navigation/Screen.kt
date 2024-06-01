package com.saeed.zanjan.receipt.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object Registration : Screen("registration")
    object Home : Screen("home")
    object Receipt : Screen("receipt")
    object CreateReceipt : Screen("createReceipt")
    object EditReceipt : Screen("editReceipt")

}