package com.saeed.zanjan.receipt.presentation.ui.registration

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.presentation.components.CircularIndeterminateProgressBar
import com.saeed.zanjan.receipt.presentation.components.CustomDropdown
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch // Import launch function

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
   viewModel:RegistrationViewModel
) {
    val loading =viewModel.loading.value
    val registerRequestState=viewModel.registerRequestState.value
    var countdownEnabled = viewModel.countdownEnabled.value
    var remainingSeconds =  viewModel.remainingSeconds.value


        var companyName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }


    var isSignInTabSelected by remember { mutableStateOf(false) }

    var sendOtpClicked by remember { mutableStateOf(false) }

    var isOtpScreenVisible by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf(false) }
    val jobTypes = listOf("خیاطی", "خشکشویی", "تعمیرات", "سایر")




        val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    NewReceiptCreatorTheme(
        displayProgressBar=loading
    ) {

    if (isOtpScreenVisible) {
        OtpScreen(
            onSendOtpClicked = {
                // Implement sending OTP logic here
            },
            onDismiss = {
                isOtpScreenVisible = false
            }
        )
    }



        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LaunchedEffect(registerRequestState){
                    isOtpScreenVisible=registerRequestState
                }

                // Tabs for sign-in and sign-up
                TabRow(
                    selectedTabIndex = if (isSignInTabSelected) 0 else 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Tab(
                        selected = isSignInTabSelected,
                        onClick = { isSignInTabSelected = true }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxHeight() // Ensure the tab fills the entire height
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            text = "ورود",
                            style = MaterialTheme.typography.bodyLarge
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
                            text = "ثبت نام",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Content based on selected tab
                if (isSignInTabSelected) {
                    // Sign In tab
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phoneNumber ->
                            phone = phoneNumber
                        },
                        label = { Text("شماره تلفن") },
                        isError = (phone.isEmpty() || phone.length < 11) && sendOtpClicked,

                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )

                        Button(
                            enabled = !countdownEnabled,
                            onClick = {
                                // Sign In button clicked
                                sendOtpClicked = true
                                if (phone.isEmpty() || phone.length < 11) {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("شماره تلفن را به درستی وارد کنید")
                                    }

                                } else {


                                    viewModel.loginRequest(
                                        phoneNumber = phone,
                                        snackbarHostState=snackbarHostState
                                    )

                                }

                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = if (countdownEnabled) " بعد از  $remainingSeconds ثانیه تلاش کنید. " else "ثبت", style = MaterialTheme.typography.bodyLarge)
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
                            phone = phoneNumber
                        },
                        label = { Text("شماره تلفن") },
                        isError = (phone.isEmpty() || phone.length < 11) && sendOtpClicked,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = userId,
                        onValueChange = { userId = it },
                        label = { Text("آیدی پیج کاری") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = error,

                        )
                    CustomDropdown(
                        items =jobTypes,
                        selectedItem = jobType,
                        onItemSelected = { selected ->
                            jobType = selected
                        },
                        label = "عنوان شغلی را انتخاب کنید",
                        modifier = Modifier.fillMaxWidth(),
                        isError = jobType == "" && sendOtpClicked
                    )

                    Button(
                        enabled = !countdownEnabled,
                        onClick = {
                            sendOtpClicked = true

                            if (phone.isEmpty() || phone.length < 11) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("شماره تلفن را به درستی وارد کنید")
                                }

                            } else {

                                    val registrationInfo = RegistrationInfo(
                                        companyName = companyName,
                                        address = address,
                                        phone = phone,
                                        userId = userId,
                                        jobType = jobType
                                    )
                                viewModel.registerRequest(
                                    registrationInfo = registrationInfo,
                                    snackbarHostState=snackbarHostState

                                )
                            }

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (countdownEnabled) " بعد از  $remainingSeconds ثانیه تلاش کنید. " else "ثبت", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}


