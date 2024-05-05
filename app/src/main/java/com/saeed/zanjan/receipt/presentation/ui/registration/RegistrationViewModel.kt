package com.saeed.zanjan.receipt.presentation.ui.registration

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.interactor.UserRegistration
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.utils.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
@Inject
constructor(
    private val userRegistration: UserRegistration,
    private val connectivityManager: ConnectivityManager,
    ):ViewModel() {
    private val rateLimit=60
    val loading = mutableStateOf(false)
    val registerRequestState= mutableStateOf(false)
    var countdownEnabled =  mutableStateOf(false)
    var remainingSeconds = mutableStateOf(rateLimit)

    fun registerRequest(
        registrationInfo: RegistrationInfo,
        snackbarHostState: SnackbarHostState
    ){
       userRegistration.sendRegistrationInfoToServer(
           registrationInfo,
           isNetworkAvailable = connectivityManager.isNetworkAvailable.value

       ).onEach { dataState ->
           dataState.loading.let {
               loading.value=it
           }
           dataState.data?.let {
               countdownEnabled.value = true
               startCountdownTimer()
               registerRequestState.value=true
           }
           dataState.error?.let {
             //  enqueueSnackbar(it ?: "An unknown error occurred")
               snackbarHostState.showSnackbar(it)

           }
       }.catch {
           snackbarHostState.showSnackbar(it.message ?: "خطای ارتباط" )
           loading.value=false
       }.launchIn(viewModelScope)
    }

    fun loginRequest(
        phoneNumber:String,
        snackbarHostState: SnackbarHostState
    ){
        userRegistration.loginRequestToServer(
            phoneNumber=phoneNumber,
            isNetworkAvailable =connectivityManager.isNetworkAvailable.value
        ).onEach {dataState->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                countdownEnabled.value = true
                startCountdownTimer()
                registerRequestState.value=true
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }
        }.catch {
            snackbarHostState.showSnackbar("خطای ارتباط")

            loading.value=false
        }.launchIn(viewModelScope)
    }

    fun senOtp(
        otpData: OtpData,
        snackbarHostState: SnackbarHostState
    ){
        userRegistration.otpCheck(
            otpData=otpData,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value
        ).onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {

                snackbarHostState.showSnackbar("ورود موفق")
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }

        }.catch {

        }.launchIn(viewModelScope)

    }


    private fun startCountdownTimer() {
        // Start countdown timer for one minute
        viewModelScope.launch {
            for (i in rateLimit downTo 1) {
                remainingSeconds.value = i
                delay(1000) // Delay for 1 second
            }
            // After one minute, reset the countdownEnabled flag
            countdownEnabled.value = false
            registerRequestState.value=false
            // Reset remainingSeconds
            remainingSeconds.value = rateLimit
        }
    }
}