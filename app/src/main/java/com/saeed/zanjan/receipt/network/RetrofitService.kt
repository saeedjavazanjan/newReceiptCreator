package com.saeed.zanjan.receipt.network

import com.saeed.zanjan.receipt.domain.models.OtpData
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.network.model.LoginResponse
import com.saeed.zanjan.receipt.network.model.OtpDataDto
import com.saeed.zanjan.receipt.network.model.ProfileDataDto
import com.saeed.zanjan.receipt.network.model.RegistrationInfoDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
    @POST("users/signUp")
    suspend fun register(@Body registrationInfo: RegistrationInfoDto): Response<String>

    @POST("users/updateProfile")
    suspend fun updateProfileData(
        @Header("Authorization") token: String?,
        @Body profileDataDto: ProfileDataDto): Response<String>

    @POST("users/signIn")
    suspend fun login(@Body otpData: OtpDataDto):Response<String>

    @POST("users/signInPasswordCheck")
    suspend fun signInOtpCheck(@Body otpData: OtpDataDto):Response<LoginResponse>
    @POST("users/signUpPasswordCheck")
    suspend fun signUpOtpCheck(@Body registrationInfo: RegistrationInfoDto):Response<LoginResponse>


    @Multipart
    @POST("backup/uploadDb")
    suspend fun uploadDatabase(
        @Header("Authorization") token: String?,
        @Part dataBase: MultipartBody.Part,
    ): Response<String>


    @GET("backup/downloadDb")
    suspend fun downloadDatabase(
        @Header("Authorization") token: String?
    ): Response<ResponseBody>
}