package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photography")

data class PhotographyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "status")
    var status:Int? ,

    @ColumnInfo(name = "name")
    var name:String ,

    @ColumnInfo(name = "phone")
    var phone:String?,

    @ColumnInfo(name = "OrderName")
    var orderName:String?,

    @ColumnInfo(name = "OrderSize")
    var orderSize:String?,

    @ColumnInfo(name = "OrderNumber")
    var orderNumber:String?,

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
photography="CREATE  TABLE  IF NOT EXISTS \"photography\" (" +
"\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
"\"status\" INTEGER , " +
"\"name\" TEXT NOT NULL  , " +
"\"phone\" TEXT, " +
"\"OrderName\" TEXT, " +
"\"OrderSize\" TEXT, " +
"\"OrderNumber\" TEXT, " +
"\"deliveryTime\" TEXT, " +
"\"receiptTime\" TEXT, " +
"\"cost\" TEXT, " +
"\"prepayment\" TEXT)";*/
