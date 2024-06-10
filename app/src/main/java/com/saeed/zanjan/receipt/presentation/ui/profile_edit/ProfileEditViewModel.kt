package com.saeed.zanjan.receipt.presentation.ui.profile_edit

import android.content.SharedPreferences
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.saeed.zanjan.receipt.domain.models.ProfileData
import com.saeed.zanjan.receipt.network.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel

 @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor

 ):ViewModel() {

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
        companyPhone.value=sharedPreferences.getString("COMPANY_PHONE","")!!
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

    suspend fun saveData(
        profileData: ProfileData,
        snackbarHostState: SnackbarHostState
    ) {
        try {
            editor.putString("AVATAR_URI", profileData.avatar )
                .putString("COMPANY", profileData.companyName )
                .putString("ADDRESS",profileData.companyAddress )
                .putString("PHONE", profileData.companyPhone )
                .putString("CHANNEL_LINK", profileData.companyLink)
                .putString("COMPANY_RULES", profileData.companyRules)
                .putInt("JOB_SUBJECT", getIdOfJob(profileData.jobType))
                .commit()
            snackbarHostState.showSnackbar("اطلاعات با موفقیت ذخیره شد.")
            dataSaveStatus.value=true
            avatar.value=profileData.avatar
            companyName.value=profileData.companyName
            companyPhone.value=profileData.companyPhone
            companyAddress.value=profileData.companyAddress
            companyLink.value=profileData.companyLink
            companyRules.value=profileData.companyRules
            jobType.value=profileData.jobType

        }catch (e:Exception){
            snackbarHostState.showSnackbar("خطای ذخیره اطلاعات")

        }

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