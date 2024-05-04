package com.saeed.zanjan.receipt.network

import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.network.model.RegistrationInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("registration")
    suspend fun register(@Body registrationInfo: RegistrationInfoDto): Response<String>

    @POST("login")
    suspend fun login(@Body phoneNumber:String):Response<String>
}