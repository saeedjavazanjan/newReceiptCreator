package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "confectionery")

data class ConfectioneryEntity(
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

    @ColumnInfo(name = "OrderSpecification")
    var orderSpecification:String?,

    @ColumnInfo(name = "OrderWeight")
    var orderWeight:String?,

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
confectionery="CREATE  TABLE  IF NOT EXISTS \"confectionery\" (" +
"\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
"\"status\" INTEGER , " +
"\"name\" TEXT NOT NULL , " +
"\"phone\" TEXT, " +
"\"OrderName\" TEXT, " +
"\"OrderSpecification\" TEXT, " +
"\"OrderWeight\" TEXT, " +
"\"Description\" TEXT, " +
"\"deliveryTime\" TEXT, " +
"\"receiptTime\" TEXT, " +
"\"cost\" TEXT, " +
"\"prepayment\" TEXT)";*/
