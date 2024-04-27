package com.saeed.zanjan.receipt

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.saeed.zanjan.receipt.domain.RegistrationInfo
import com.saeed.zanjan.receipt.domain.User
import com.saeed.zanjan.receipt.presentation.ui.registration.OtpScreen
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationScreen
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceiptCreatorApp()
//TestTabRow()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReceiptCreatorApp() {
    var loggedInUser by remember { mutableStateOf<User?>(null) }
    var isOtpSent by remember { mutableStateOf(false) }

    // Callback for signing in
    val signInCallback: (String) -> Unit = { phoneNumber ->
        // Handle sign-in logic here
        // For demo, let's assume sign-in is successful and set the logged-in user
        loggedInUser = User(phoneNumber = phoneNumber)
    }

    // Callback for signing up
    val signUpCallback: (RegistrationInfo) -> Unit = { registrationInfo ->
        // Handle sign-up logic here
        // For demo, let's assume sign-up is successful and set the logged-in user
        loggedInUser = User(phoneNumber = registrationInfo.phone)
    }

    // Callback for sending OTP
    val sendOtpCallback: () -> Unit = {
        // Handle sending OTP logic here
        // For demo, let's just print a log message
        Log.d("ReceiptCreatorApp", "OTP sent")
        isOtpSent = true
    }

    /*Scaffold(
        topBar = {
            TopAppBar(title = { Text("Receipt Creator") })
        }
    ) {*/
        if (loggedInUser == null && !isOtpSent) {
            // Show registration screen if user is not logged in and OTP is not sent
            RegistrationScreen(
                onSignInClicked = signInCallback,
                onSignUpClicked = signUpCallback,
                onSendOtpClicked = sendOtpCallback
            )
        } else if (loggedInUser == null && isOtpSent) {
            // Show OTP screen if user is not logged in and OTP is sent
            OtpScreen(sendOtpCallback)
        } else {
            // Show authenticated content if user is logged in
            // For demo, let's just show a text message
            Text("Welcome, ${loggedInUser?.phoneNumber}")
        }
   // }
}

@Composable
fun TestTabRow() {
    val tabTitles = listOf("Tab 1", "Tab 2")

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Gray,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                ) {
                    Text(text = title)
                }
            }
        }
    }
}