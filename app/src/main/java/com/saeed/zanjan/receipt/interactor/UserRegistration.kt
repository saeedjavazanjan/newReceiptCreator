package com.saeed.zanjan.receipt.interactor

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.models.ProfileData
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.network.model.LoginResponse
import com.saeed.zanjan.receipt.network.model.OtpDataDtoMapper
import com.saeed.zanjan.receipt.network.model.ProfileDataDtoMapper
import com.saeed.zanjan.receipt.network.model.RegistrationInfoDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response

class UserRegistration(
    private val retrofitService: RetrofitService,
    private val registrationDtoMapper: RegistrationInfoDtoMapper,
    private val otpDataDtoMapper: OtpDataDtoMapper,
    private val profileDataDtoMapper: ProfileDataDtoMapper,
    private val sharedPreferences: SharedPreferences,
    private val editor: Editor
    ) {


    @SuppressLint("CommitPrefEdits")
    fun sendRegistrationInfoToServer(
        registrationInfo: RegistrationInfo,
        isNetworkAvailable: Boolean

    )
    :Flow<DataState<String?>> = flow {
        if(isNetworkAvailable){
            emit(DataState.loading())
            try {
                val response =retrofitService.register(registrationDtoMapper.mapFromDomainModel(registrationInfo))
                if (response.isSuccessful){
                    emit(DataState.success(response.body()))

                }else if (response.code()==429){
                    emit(DataState.error("خظا!! بعد از چند دقیقه مجددا تلاش کنید"))

                }
                else {

                    try {
                        val errMsg = response.errorBody()?.string()?.let {
                            JSONObject(it).getString("error") // or whatever your message is
                        } ?: run {
                            emit(DataState.error(response.code().toString()))
                        }
                        emit(DataState.error(errMsg.toString()))
                    } catch (e: Exception) {
                        emit(DataState.error(e.message ?: "خطای سرور"))


                    }

                }
            }catch (e:Exception){
                emit(DataState.error(e.message ?: "خطای ارتباط"))
            }


    }else{
        emit(DataState.error("شما به اینترنت دسترسی ندارید"))

    }
        }


    fun loginRequestToServer(
        otpData: OtpData,
        isNetworkAvailable: Boolean
    ):Flow<DataState<String?>> = flow {
        if (isNetworkAvailable){
            emit(DataState.loading())
            try {
                val response =retrofitService.login(otpDataDtoMapper.mapFromDomainModel(otpData))
                if (response.isSuccessful)
                    emit(DataState.success(response.body()))
                else if (response.code()==429){
                    emit(DataState.error("خطا!! بعد از چند دقیقه مجددا تلاش کنید"))

                }  else {

                    try {
                        val errMsg = response.errorBody()?.string()?.let {
                            JSONObject(it).getString("error") // or whatever your message is
                        } ?: run {
                            emit(DataState.error(response.code().toString()))
                        }
                        emit(DataState.error(errMsg.toString()))
                    } catch (e: Exception) {
                        emit(DataState.error(e.message ?: "خطای سرور"))


                    }

                }
            }catch (e:Exception){
                emit(DataState.error(e.message ?: "خطای ارتباط"))
            }


        }else{
            emit(DataState.error("شما به اینترنت دسترسی ندارید"))

        }
    }



    fun otpCheck(
        otpData: OtpData,
        registrationInfo: RegistrationInfo,
        isNetworkAvailable: Boolean,
        isSignIn:Boolean
    ):Flow<DataState<LoginResponse?>> = flow {
        if(isNetworkAvailable){
            emit(DataState.loading())
            try {
                var response:Response<LoginResponse>?=null
                if (isSignIn)
                 response =retrofitService.signInOtpCheck(otpDataDtoMapper.mapFromDomainModel(otpData))
                else
                 response =retrofitService.signUpOtpCheck(registrationDtoMapper.mapFromDomainModel(registrationInfo))

                if (response.isSuccessful){

                    try {
                        saveData(response.body())


                        emit(DataState.success(response.body()))
                    }catch (e:Exception){
                        emit(DataState.error(e.message ?: "خطای ذخیره سازی"))
                    }

                }


                else {

                    try {
                        val errMsg = response.errorBody()?.string()?.let {
                            JSONObject(it).getString("error") // or whatever your message is
                        } ?: run {
                            emit(DataState.error(response.code().toString()))
                        }
                        emit(DataState.error(errMsg.toString()))
                    } catch (e: Exception) {
                        emit(DataState.error(e.message ?: "خطای سرور"))


                    }

                }
            }catch (e:Exception){
                emit(DataState.error(e.message ?: "خطای ارتباط"))

            }
        }else{
            emit(DataState.error("شما به اینترنت دسترسی ندارید"))

        }

    }

    private fun saveData(body: LoginResponse?) {
        editor.putString("COMPANY", body!!.userData!!.name )
            .putString("ADDRESS",body!!.userData!!.address )
            .putString("PHONE", body!!.userData!!.phoneNumber )
            // due to the first version company phone key is channel link and not changed
            .putString("CHANNEL_LINK", body!!.userData!!.pageId)
            .putInt("JOB_SUBJECT", jobId(body!!.userData!!.jobTitle))
            .putString("JWTToken","Bearer ${body!!.tok}")
            .putBoolean("SAVED", true)
            .putBoolean("SAVED_IN_SERVER",true)
            .commit()

    }

    private fun jobId(jobTitle: String?):Int {
        var jobId=-1
        when(jobTitle){
            "تعمیرات موبایل" -> jobId = 0
            "تعمیرات کامپیوتر" -> jobId = 1
            "تعمیرات لوازم برقی" -> jobId = 2
            "خیاطی" -> jobId = 3
            "جواهر سازی" -> jobId = 4
            "عکاسی" -> jobId = 5
            "خشکشویی" -> jobId = 6
            "قنادی" -> jobId = 7
            "سایر مشاغل" -> jobId = 8
        }
        return jobId
    }


    fun updateUserData(
        isNetworkAvailable: Boolean,
        profileData: ProfileData
    ):Flow<DataState<String>> = flow {
        emit(DataState.loading())

    if(isNetworkAvailable){
        try {
             val token = sharedPreferences.getString("JWTToken","")

            val response=retrofitService.updateProfileData(
                token=token,
                profileDataDtoMapper.mapFromDomainModel(profileData)
            )
            if (response.isSuccessful){
                emit(DataState.success("با موفقیت به روز رسانی شد."))
            }
            else if (response.code() == 401) {
                emit(DataState.error("شما دسترسی لازم را ندارید"))

            } else {

                try {
                    val errMsg = response.errorBody()?.string()?.let {
                        JSONObject(it).getString("error") // or whatever your message is
                    } ?: run {
                        emit(DataState.error(response.code().toString()))
                        Log.i("PROFILEUPDATE","1")

                    }
                    emit(DataState.error(errMsg.toString()))
                    Log.i("PROFILEUPDATE","2")

                } catch (e: Exception) {
                    emit(DataState.error(e.message ?: "خطای سرور"))

                    Log.i("PROFILEUPDATE","3")

                }

            }




        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))
            Log.i("PROFILEUPDATE",e.message.toString())

        }
    }else{
        emit(DataState.error("ارتباط شما با اینترنت برقرار نیست."))
    }



    }
}