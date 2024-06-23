package com.saeed.zanjan.receipt.presentation.ui.registration

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.presentation.components.CustomDropdown
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch // Import launch function

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
   viewModel:RegistrationViewModel,
   navigateToHome:()->Unit
) {



    val loading =viewModel.loading.value
    val registerRequestState=viewModel.registerRequestState.value
    val countdownEnabled = viewModel.countdownEnabled.value
    val remainingSeconds =  viewModel.remainingSeconds.value
    val successLogin=viewModel.successLogin
    NewReceiptCreatorTheme(
        displayProgressBar=loading,
        themColor = Color.Transparent
    ) {

        LaunchedEffect(successLogin.value){
            if(successLogin.value)
            navigateToHome()
        }
        var companyName by remember { mutableStateOf(viewModel.companyName.value) }
        var phone by remember { mutableStateOf(viewModel.phone.value) }
        var address by remember { mutableStateOf(viewModel.companyAddress.value) }
        var companyLink by remember { mutableStateOf(viewModel.companyLink.value) }
        var jobType by remember { mutableStateOf(viewModel.jobType.value) }

    var isSignInTabSelected by remember { mutableStateOf(false) }

    var sendOtpClicked by remember { mutableStateOf(false) }

    var isOtpScreenVisible by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf(false) }
    val jobTypes = listOf(
        "تعمیرات موبایل",
        "تعمیرات کامپیوتر",
        "تعمیرات لوازم برقی",
        "خیاطی",
        "جواهر سازی",
        "عکاسی",
        "خشکشویی",
        "قنادی",
        "سایر مشاغل",
        )




        val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

        val context= LocalContext.current

    if (isOtpScreenVisible) {
        OtpScreen(
            onSendOtpClicked = {enteredOtp->
                val otpData=OtpData(
                    phoneNumber = phone,
                    password = enteredOtp
                )
                val registrationInfo = RegistrationInfo(
                    companyName = companyName,
                    password=enteredOtp,
                    address = address,
                    phone = phone,
                    userId = companyLink,
                    jobType = jobType
                )
                   viewModel.senOtp(
                       otpData=otpData,
                       registrationInfo=registrationInfo,
                       snackbarHostState = snackbarHostState,
                       isSignIn=isSignInTabSelected,
                       context=context
                   )

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
                        .padding(10.dp),
                    contentColor = CustomColors.darkBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[if (isSignInTabSelected) 0 else 1])
                                .fillMaxWidth()
                                .height(4.dp),
                            color = CustomColors.darkBlue
                        )
                    }

                ) {
                    Tab(
                        selected = isSignInTabSelected,
                        onClick = { isSignInTabSelected = true },
                        selectedContentColor = CustomColors.darkBlue,
                        unselectedContentColor = CustomColors.gray

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
                        onClick = { isSignInTabSelected = false },
                        selectedContentColor = CustomColors.darkBlue,
                        unselectedContentColor = CustomColors.gray
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
                            if (phoneNumber.length <= 11) {
                                phone = phoneNumber
                            }
                        },
                        label = { Text("شماره تلفن") },
                        isError = (phone.isEmpty() || phone.length < 11) && sendOtpClicked,

                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp) ,// Adjust the radius for rounded corners
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent, // Set light gray background
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                            unfocusedBorderColor = Color.Transparent,// Transparent to clear the outline
                            focusedLabelColor = CustomColors.darkBlue

                        ),
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

                                    val otpData=OtpData(
                                        phoneNumber = phone,
                                        password = ""
                                    )
                                    viewModel.loginRequest(
                                        otpData = otpData,
                                        snackbarHostState=snackbarHostState
                                    )

                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CustomColors.darkBlue
                            )
                        ) {
                            Text(
                                text = if (countdownEnabled) " بعد از  $remainingSeconds ثانیه تلاش کنید. "
                                else "ثبت", style = MaterialTheme.typography.titleLarge)
                        }




                } else {
                    // Sign Up tab
                    OutlinedTextField(
                        value = companyName,
                        onValueChange = {
                            if (it.length <= 20)
                                companyName = it
                                        },
                        label = { Text("نام مجموعه") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp) ,// Adjust the radius for rounded corners
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent, // Set light gray background
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                            unfocusedBorderColor = Color.Transparent ,// Transparent to clear the outline
                            focusedLabelColor = CustomColors.darkBlue

                        ),
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = {
                            if (it.length <= 500)
                                address = it
                                        },
                        label = { Text("آدرس") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp) ,// Adjust the radius for rounded corners
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent, // Set light gray background
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                            unfocusedBorderColor = Color.Transparent ,// Transparent to clear the outline
                            focusedLabelColor = CustomColors.darkBlue

                        ),
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phoneNumber ->
                            if (phoneNumber.length <= 11)
                            phone = phoneNumber
                        },
                        label = { Text("شماره تلفن") },
                        isError = (phone.isEmpty() || phone.length < 11) && sendOtpClicked,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp) ,// Adjust the radius for rounded corners
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent, // Set light gray background
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                            unfocusedBorderColor = Color.Transparent, // Transparent to clear the outline
                            focusedLabelColor = CustomColors.darkBlue
                        ),
                    )
                    OutlinedTextField(
                        value = companyLink,
                        onValueChange = {
                            if (it.length <= 20)
                            companyLink = it

                                        },
                        label = { Text("لینک ارتباطی") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = error,
                        singleLine = true,
                        shape = RoundedCornerShape(30.dp) ,// Adjust the radius for rounded corners
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent, // Set light gray background
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                            unfocusedBorderColor = Color.Transparent ,
                            focusedLabelColor = CustomColors.darkBlue
                            // Transparent to clear the outline
                        ),

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
                            } else if (jobType == "") {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("عنوان شغلی را انتخاب کنید")
                                    }
                            } else {

                                    val registrationInfo = RegistrationInfo(
                                        companyName = companyName,
                                        password="",
                                        address = address,
                                        phone = phone,
                                        userId = companyLink,
                                        jobType = jobType
                                    )
                                viewModel.registerRequest(
                                    registrationInfo = registrationInfo,
                                    snackbarHostState=snackbarHostState

                                )
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.darkBlue
                        )
                    ) {
                        Text(
                            text = if (countdownEnabled) " بعد از  $remainingSeconds ثانیه تلاش کنید. "
                            else "ثبت", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}


