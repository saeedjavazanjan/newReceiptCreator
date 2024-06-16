package com.saeed.zanjan.receipt.cash.model

import android.icu.util.CurrencyAmount
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")

data class CustomerEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "phone")
    val phoneNumber: String?,

    @ColumnInfo(name = "prepayment")
    val payedAmount:String?,

    @ColumnInfo(name = "cost")
    val totalAmount: String?
)
