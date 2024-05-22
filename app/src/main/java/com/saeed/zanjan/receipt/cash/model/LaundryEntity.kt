package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laundry")

data class LaundryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "status")
    var status:Int? ,

    @ColumnInfo(name = "name")
    var name:String ,

    @ColumnInfo(name = "phone")
    var phone:String?,

    @ColumnInfo(name = "loanerName")
    var loanerName:String?,

    @ColumnInfo(name = "OrderType")
    var orderType:String?,

    @ColumnInfo(name = "Description")
    var description:String?,

  @ColumnInfo(name = "deliveryTime")
    var deliveryTime:String?,

  @ColumnInfo(name = "receiptTime")
    var receiptTime:String?,

    @ColumnInfo(name = "cost")
    var cost:String?,

    @ColumnInfo(name = "prepayment")
    var prepayment:String?

)

/*
laundry="CREATE  TABLE  IF NOT EXISTS \"laundry\" (" +
"\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
"\"status\" INTEGER , " +
"\"name\" TEXT NOT NULL , " +
"\"phone\" TEXT, " +
"\"loanerName\" TEXT, " +
"\"OrderType\" TEXT, " +
"\"Description\" TEXT, " +
"\"deliveryTime\" TEXT, " +
"\"receiptTime\" TEXT, " +
"\"cost\" TEXT, " +
"\"prepayment\" TEXT)";*/
