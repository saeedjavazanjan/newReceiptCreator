package com.saeed.zanjan.receipt.network.model

import com.google.gson.annotations.SerializedName
data class LoginResponse(

    @field:SerializedName("tok")
    val tok: String? = null,

    @field:SerializedName("userData")
    val userData: UserData? = null
)

data class UserData(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("jobTitle")
    val jobTitle: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("pageId")
    val pageId: String? = null
)