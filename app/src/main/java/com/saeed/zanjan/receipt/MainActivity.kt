package com.saeed.zanjan.receipt

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saeed.zanjan.receipt.domain.RegistrationInfo
import com.saeed.zanjan.receipt.domain.User
import com.saeed.zanjan.receipt.presentation.components.Navigation
import com.saeed.zanjan.receipt.presentation.ui.registration.OtpScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationScreen
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

          //  val scaffoldState= rememberScaffoldState()
            val snackbarHostState = SnackbarHostState()

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Scaffold(
                    topBar = {
                        /*  TopBar(
                            topBarState = topBarState,
                            onNotifClicked = {
                                navController.navigate(Screen.Notifications.route)

                            },
                            badgeCount=0
                        )*/
                    },
                    bottomBar = {

                        /*   BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "پروفایل",
                                    route = Screen.AuthorProfile.route,
                                    icon = Icons.Default.Person
                                ),
                                BottomNavItem(
                                    name = "فروشگاه",
                                    route = Screen.Courses.route,
                                    icon = Icons.Default.ShoppingCart
                                ),
                                BottomNavItem(
                                    name = "خانه",
                                    route = Screen.Home.route,
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "جست و جو",
                                    route = Screen.Search.route,
                                    icon = Icons.Default.Search
                                ),
                                BottomNavItem(
                                    name = "آموزشگاه",
                                    route = Screen.School.route,
                                    icon = Icons.Default.DateRange
                                )

                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route) {
                                    *//*  launchSingleTop=true
                                popUpTo(Screen.HomeSubNavigation.route){
                                 //   inclusive=true
                                    saveState = true
                                }
                                restoreState=true*//*
                                    launchSingleTop = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }

                            },
                            bottomBarState = bottomBarState,

                            )*/
                    },
                    snackbarHost = { snackbarHostState }

                ) {
                    Box(modifier = Modifier.padding(it)) {
                        Navigation(
                            navController = navController,

                            /*   appDataStore = appDataStore,
                               connectivityManager = connectivityManager,
                               scaffoldState = scaffoldState*/

                        )
                       /* if (openDialog.value) {
                            ExitAlertDialog(navController, {
                                openDialog.value = it
                            }, {
                                finish()
                            })

                        }*/
                    }
                }

            }
            //ReceiptCreatorApp()
//TestTabRow()
        }
    }
}

/*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReceiptCreatorApp() {
    var loggedInUser = remember { mutableStateOf<User?>(null) }
    var isOtpSent by remember { mutableStateOf(false) }

    // Callback for signing in
    val signInCallback: (String) -> Unit = { phoneNumber ->
        // Handle sign-in logic here
        // For demo, let's assume sign-in is successful and set the logged-in user
        loggedInUser.value = User(phoneNumber = phoneNumber)
    }

    // Callback for signing up
    val signUpCallback: (RegistrationInfo) -> Unit = { registrationInfo ->
        // Handle sign-up logic here
        // For demo, let's assume sign-up is successful and set the logged-in user
        loggedInUser.value = User(phoneNumber = registrationInfo.phone)
    }

    // Callback for sending OTP
    val sendOtpCallback: () -> Unit = {
        // Handle sending OTP logic here
        // For demo, let's just print a log message
        Log.d("ReceiptCreatorApp", "OTP sent")
        isOtpSent = true
    }

    */
/*Scaffold(
        topBar = {
            TopAppBar(title = { Text("Receipt Creator") })
        }
    ) {*//*

        if (loggedInUser.value == null && !isOtpSent) {
            // Show registration screen if user is not logged in and OTP is not sent
            RegistrationScreen(
                onSignInClicked = signInCallback,
                onSignUpClicked = signUpCallback,
                onSendOtpClicked = sendOtpCallback
            )
        } else if (loggedInUser.value == null && isOtpSent) {
            // Show OTP screen if user is not logged in and OTP is sent
            OtpScreen(sendOtpCallback)
        } else if(loggedInUser.value !=null &&isOtpSent) {
            OtpScreen(sendOtpCallback)

        }
   // }
}

*/
