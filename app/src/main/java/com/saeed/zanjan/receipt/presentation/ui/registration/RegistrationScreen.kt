package com.saeed.zanjan.receipt.presentation.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.domain.RegistrationInfo
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch // Import launch function

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onSignInClicked: (String) -> Unit,
    onSignUpClicked: (RegistrationInfo) -> Unit,
  //  onSendOtpClicked: () -> Unit
) {
    var companyName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }
    var isSignInTabSelected by remember { mutableStateOf(false) }

    val snackbarHostState = SnackbarHostState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tabs for sign-in and sign-up
        TabRow(
            selectedTabIndex = if (isSignInTabSelected) 0 else 1,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(10.dp)        ) {
            Tab(
                selected = isSignInTabSelected,
                onClick = { isSignInTabSelected = true }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxHeight() // Ensure the tab fills the entire height
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = "Sign In",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Tab(
                selected = !isSignInTabSelected,
                onClick = { isSignInTabSelected = false }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxHeight() // Ensure the tab fills the entire height
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = "ورود",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Content based on selected tab
        if (isSignInTabSelected) {
            // Sign In tab
            OutlinedTextField(
                value = phone,
                onValueChange = { phoneNumber ->
                    phone=phoneNumber
                },
                label = { Text("شماره تلفن") },
                isError = phone.isEmpty() || phone.length < 11,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    // Sign In button clicked
                    onSignInClicked(Screen.Otp.route)

                    // Switch to OTP screen
                   // onSendOtpClicked()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ورود")
            }
        } else {
            // Sign Up tab
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("نام مجموعه") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("آدرس") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phoneNumber ->
                  phone=phoneNumber
                },
                label = { Text("شماره تلفن") },
                isError = phone.isEmpty() || phone.length < 11,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("آیدی پیج کاری") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = jobType,
                onValueChange = { jobType = it },
                label = { Text("عنوان شغل") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (phone.isEmpty() || phone.length < 11) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("شماره تلفن را به درستی وارد کنید")
                        }

                    }else{
                        val registrationInfo = RegistrationInfo(
                            companyName = companyName,
                            address = address,
                            phone = phone,
                            userId = userId,
                            jobType = jobType
                        )
                        // Handle sign-up logic
                        onSignUpClicked(registrationInfo)
                        // Switch to OTP screen
                     //   onSendOtpClicked()
                    }
                    // Sign Up button clicked

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ثبت نام")
            }
        }
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onSignInClicked: (String) -> Unit,
    onSignUpClicked: (RegistrationInfo) -> Unit,
    onSendOtpClicked: () -> Unit
) {
    var companyName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }
    var isSignInTabSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tabs for sign-in and sign-up
        TabRow(

            selectedTabIndex = if (isSignInTabSelected) 0 else 1,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(10.dp),
        ) {
            Tab(
                selected = isSignInTabSelected,
                onClick = { isSignInTabSelected = true }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxHeight() // Ensure the tab fills the entire height
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = "Sign In")
            }
            Tab(

                selected = !isSignInTabSelected,
                onClick = { isSignInTabSelected = false }
            ) {
                Text( modifier = Modifier
                    .fillMaxHeight() // Ensure the tab fills the entire height
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = "Sign Up")
            }
        }

        // Content based on selected tab
        if (isSignInTabSelected) {
            // Sign In tab
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") }
            )
            Button(
                onClick = {
                    // Sign In button clicked
                    onSignInClicked(phone)
                    // Switch to OTP screen
                    onSendOtpClicked()
                }
            ) {
                Text("Sign In")
            }
        } else {
            // Sign Up tab
            TextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("Company Name") }
            )
            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") }
            )
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") }
            )
            TextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("User ID") }
            )
            TextField(
                value = jobType,
                onValueChange = { jobType = it },
                label = { Text("Job Type") }
            )
            Button(
                onClick = {
                    // Sign Up button clicked
                    val registrationInfo = RegistrationInfo(
                        companyName = companyName,
                        address = address,
                        phone = phone,
                        userId = userId,
                        jobType = jobType
                    )
                    // Handle sign-up logic
                    onSignUpClicked(registrationInfo)
                    // Switch to OTP screen
                    onSendOtpClicked()
                }
            ) {
                Text("Sign Up")
            }
        }
    }
}
*/
