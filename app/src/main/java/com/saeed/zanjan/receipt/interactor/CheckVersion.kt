package com.saeed.zanjan.receipt.interactor

import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.network.RetrofitService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckVersion(
   private val retrofitService: RetrofitService
) {




    fun checkVersionOnServer(
        isNetworkAvailable: Boolean

    ):Flow<DataState<String?>> = flow {

        if(isNetworkAvailable){
            emit(DataState.loading())
            try {
                val result=retrofitService.checkVersion()
                delay(3000)
                emit(DataState.success(result.body()))
            }catch (e:Exception){
                emit(DataState.error(e.message.toString()))
            }
        }else{
            delay(3000)
            emit(DataState.error("ارتباط با اینترنت برقرار نیست"))

        }


    }
}