package com.saeed.zanjan.receipt.interactor

import android.content.SharedPreferences
import android.net.NetworkRequest
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class RequestPersonalPanel(
    val sharedPreferences: SharedPreferences,
    val retrofitService: RetrofitService
) {

    private val token = sharedPreferences.getString("JWTToken","")




    fun requestPanel():Flow<DataState<String?>> = flow{
        emit(DataState.loading())
        try {

            val response=retrofitService.requestPanel(token)

            if (response.isSuccessful) {
                emit(DataState.success(response.body()))
            }else {
                try {
                    val errMsg = response.errorBody()?.string()?.let {
                        JSONObject(it).getString("error") // or whatever your message is
                    } ?: run {
                        emit(DataState.error(response.code().toString()))
                    }
                    emit(DataState.error(errMsg.toString()))
                } catch (e: Exception) {
                    emit(DataState.error(e.message.toString()))
                }
            }
            }catch (e:Exception){
            emit(DataState.error(e.message.toString()))

        }


    }






}