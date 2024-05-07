package com.saeed.zanjan.receipt.network

import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.network.model.LoginResponse
import com.saeed.zanjan.receipt.network.model.OtpDataDto
import com.saeed.zanjan.receipt.network.model.RegistrationInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("users/signUp")
    suspend fun register(@Body registrationInfo: RegistrationInfoDto): Response<String>

    @POST("users/signIn")
    suspend fun login(@Body otpData: OtpDataDto):Response<String>

    @POST("users/signInPasswordCheck")
    suspend fun signInOtpCheck(@Body otpData: OtpDataDto):Response<LoginResponse>
    @POST("users/signUpPasswordCheck")
    suspend fun signUpOtpCheck(@Body registrationInfo: RegistrationInfoDto):Response<LoginResponse>
}