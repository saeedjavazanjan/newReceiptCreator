package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo

data class TemporaryCustomerDto(

    val name: String?,
    val phone: String?,
    val prepayment:String?,
    val cost: String?
    )
