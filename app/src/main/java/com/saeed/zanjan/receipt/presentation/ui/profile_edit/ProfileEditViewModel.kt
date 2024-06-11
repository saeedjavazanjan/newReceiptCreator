package com.saeed.zanjan.receipt.presentation.ui.profile_edit

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.saeed.zanjan.receipt.domain.models.ProfileData
import com.saeed.zanjan.receipt.interactor.UserRegistration
import com.saeed.zanjan.receipt.network.model.LoginResponse
import com.saeed.zanjan.receipt.utils.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel

 @Inject constructor(
     private val sharedPreferences: SharedPreferences,
     private val editor: SharedPreferences.Editor,
     private val userRegistration: UserRegistration,
    private val connectivityManager: ConnectivityManager,

     ):ViewModel() {

    val loading= mutableStateOf(false)
    val dataSaveStatus = mutableStateOf(false)


    val avatar= mutableStateOf("")
    val companyName= mutableStateOf("")
    val companyPhone= mutableStateOf("")
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
        // due to the first version company phone key is channel link and not changed
        companyPhone.value=sharedPreferences.getString("CHANNEL_LINK","")!!
        companyAddress.value=sharedPreferences.getString("ADDRESS","")!!
        companyLink.value=sharedPreferences.getString("COMPANY_LINK","")!!
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

    suspend fun saveData(
        profileData: ProfileData,
        snackbarHostState: SnackbarHostState
    ) {
        try {
            editor.putString("AVATAR_URI", profileData.avatar )
                .putString("COMPANY", profileData.companyName )
                .putString("ADDRESS",profileData.companyAddress )
                .putString("CHANNEL_LINK", profileData.companyPhone )
                .putString("COMPANY_LINK", profileData.companyLink)
                .putString("COMPANY_RULES", profileData.companyRules)
                .putInt("JOB_SUBJECT", getIdOfJob(profileData.jobType))
                .commit()
            dataSaveStatus.value=true
            avatar.value=profileData.avatar
            companyName.value=profileData.companyName
            companyPhone.value=profileData.companyPhone
            companyAddress.value=profileData.companyAddress
            companyLink.value=profileData.companyLink
            companyRules.value=profileData.companyRules
            jobType.value=profileData.jobType
            updateInServer(profileData,snackbarHostState)
        }catch (e:Exception){
            snackbarHostState.showSnackbar("خطای ذخیره اطلاعات")
            Log.i("PROFILEUPDATE","2")

        }

    }


    fun updateInServer(profileData: ProfileData,snackbarHostState: SnackbarHostState){
        userRegistration.updateUserData(
            isNetworkAvailable =connectivityManager.isNetworkAvailable.value,
            profileData=profileData
        ).onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {

                snackbarHostState.showSnackbar(it)

            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)


            }

        }.launchIn(viewModelScope)


    }


    fun getIdOfJob(jobTitle:String):Int{
        return when( jobTitle){
            "تعمیرات موبایل"->{
                0
            }

            "تعمیرات کامپیوتر"->{
                1
            }

            "تعمیرات لوازم برقی"->{
                2
            }

            "خیاطی"->{
                3
            }

            "جواهر سازی"->{
                4
            }

            "عکاسی"->{
                5
            }

            "خشکشویی"->{
                6
            }

            "قنادی"->{
                7
            }

            "سایر مشاغل"->{
                8
            }

            else -> {
                0
            }
        }

    }
    fun restartState(){
        dataSaveStatus.value=false
    }
}