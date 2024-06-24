package com.saeed.zanjan.receipt.presentation.ui.registration

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.interactor.Backup
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
    private val sharedPreferences: SharedPreferences,
    private val backup: Backup,

    ):ViewModel() {
    private val rateLimit=60
    val loading = mutableStateOf(false)
    val registerRequestState= mutableStateOf(false)
    val successLogin= mutableStateOf(false)
    var countdownEnabled =  mutableStateOf(false)
    var remainingSeconds = mutableStateOf(rateLimit)
    val databaseSaved= mutableStateOf(false)


    val avatar= mutableStateOf("")
    val companyName= mutableStateOf("")
    val phone= mutableStateOf("")
    val companyAddress= mutableStateOf("")
    val companyLink= mutableStateOf("")
    val companyRules= mutableStateOf("")
    val jobType= mutableStateOf("")

    init {
        getDataFromSharedPreferences()
    }
    fun getDataFromSharedPreferences(){
        avatar.value=sharedPreferences.getString("AVATAR_URI","")!!
        companyName.value=sharedPreferences.getString("COMPANY","")!!
        phone.value=sharedPreferences.getString("PHONE","")!!
        companyAddress.value=sharedPreferences.getString("ADDRESS","")!!
        companyLink.value=sharedPreferences.getString("CHANNEL_LINK","")!!
        companyRules.value=sharedPreferences.getString("COMPANY_RULES","")!!
        jobType.value=
            when( sharedPreferences.getInt("JOB_SUBJECT",0)){
                0->{ "تعمیرات موبایل"}
                1->{"تعمیرات کامپیوتر"}
                2->{ "تعمیرات لوازم برقی"}
                3->{"خیاطی"}
                4->{ "جواهر سازی"}
                5->{ "عکاسی"}
                6->{ "خشکشویی"}
                7->{ "قنادی"}
                8->{ "سایر مشاغل"}
                else -> {""}
            }


    }
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
        otpData: OtpData,
        snackbarHostState: SnackbarHostState
    ){
        userRegistration.loginRequestToServer(
            otpData=otpData,
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
        registrationInfo: RegistrationInfo,
        snackbarHostState: SnackbarHostState,
        isSignIn:Boolean,
        context: Context
    ){
        userRegistration.otpCheck(
            otpData=otpData,
            registrationInfo=registrationInfo,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
            isSignIn=isSignIn
        ).onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                downloadDb(snackbarHostState)
                registerRequestState.value=false
                snackbarHostState.showSnackbar("ورود موفق")
            }
            dataState.error?.let {
              //  snackbarHostState.showSnackbar(it)
          Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            }

        }.catch {

        }.launchIn(viewModelScope)

    }

    fun downloadDb(snackbarHostState: SnackbarHostState){
        backup.downloadDatabase().onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let{
                successLogin.value=true
                fillCustomersTable(snackbarHostState)

            }
            dataState.error?.let {
                successLogin.value=true

             //   snackbarHostState.showSnackbar(it)

            }

        }.launchIn(viewModelScope)

    }


        fun fillCustomersTable(snackbarHostState: SnackbarHostState){
            backup.fillCustomerTable().onEach { dataState ->
                dataState.loading.let {
                    loading.value=it
                }
                dataState.data?.let {
                    if(it)
                        databaseSaved.value=true
                    else{
                        databaseSaved.value=false
                        loading.value=false
                    }
                }
                dataState.error?.let {
                    snackbarHostState.showSnackbar(it)
                }

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