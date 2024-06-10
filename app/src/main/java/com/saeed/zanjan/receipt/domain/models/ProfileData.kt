package com.saeed.zanjan.receipt.domain.models

import androidx.compose.runtime.mutableStateOf

data class ProfileData(
val avatar:String="",
val companyName:String="",
val companyPhone:String="",
val companyAddress:String="",
val companyLink:String="",
val companyRules:String="",
val jobType:String=""
)