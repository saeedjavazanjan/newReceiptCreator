package com.saeed.zanjan.receipt.interactor

import android.content.SharedPreferences
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.network.model.RegistrationInfoDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class UserRegistration(
    private val retrofitService: RetrofitService,
    private val dtoMapper: RegistrationInfoDtoMapper,
    private val sharedPreferences: SharedPreferences
    ) {


    fun sendRegistrationInfoToServer(
        registrationInfo: RegistrationInfo,
        isNetworkAvailable: Boolean

    )
    :Flow<DataState<String?>> = flow {
        if(isNetworkAvailable){
            emit(DataState.loading())
            try {
                val response =retrofitService.register(dtoMapper.mapFromDomainModel(registrationInfo))
                if (response.isSuccessful)
                    emit(DataState.success(response.body()))
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
        phoneNumber:String,
        isNetworkAvailable: Boolean
    ):Flow<DataState<String?>> = flow {
        if (isNetworkAvailable){
            emit(DataState.loading())
            try {
                val response =retrofitService.login(phoneNumber)
                if (response.isSuccessful)
                    emit(DataState.success(response.body()))
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
}