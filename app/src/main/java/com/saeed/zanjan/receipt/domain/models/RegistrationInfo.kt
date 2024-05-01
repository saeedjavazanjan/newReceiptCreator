package com.saeed.zanjan.receipt.domain.models

data class RegistrationInfo(
    val companyName: String,
    val address: String,
    val phone: String,
    val userId: String,
    val jobType: String
)