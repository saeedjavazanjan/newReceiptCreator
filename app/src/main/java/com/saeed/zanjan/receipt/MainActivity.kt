package com.saeed.zanjan.receipt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saeed.zanjan.receipt.presentation.components.Navigation
import com.saeed.zanjan.receipt.utils.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

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
