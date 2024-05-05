package com.saeed.zanjan.receipt.network.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("userData")
    val userData: RegistrationInfoDto? = null,

    @field:SerializedName("token")
    val token: String? = null
)