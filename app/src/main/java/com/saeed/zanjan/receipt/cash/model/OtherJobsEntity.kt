package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "otherJobs")

data class OtherJobsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "status")
    var status: Int?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "phone")
    var phone: String?,

    @ColumnInfo(name = "OrderName")
    var orderName: String?,

    @ColumnInfo(name = "Description")
    var description: String?,

    @ColumnInfo(name = "OrderNumber")
    var orderNumber: String?,

    @ColumnInfo(name = "deliveryTime")
    var deliveryTime: String?,

    @ColumnInfo(name = "receiptTime")
    var receiptTime: String?,

    @ColumnInfo(name = "cost")
    var cost: String?,

    @ColumnInfo(name = "prepayment")
    var prepayment: String?

)
/*
othersJobs="CREATE  TABLE  IF NOT EXISTS \"otherJobs\" (" +
"\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
"\"status\" INTEGER , " +
"\"name\" TEXT NOT NULL , " +
"\"phone\" TEXT, " +
"\"OrderName\" TEXT, " +
"\"Description\" TEXT, " +
"\"OrderNumber\" TEXT, " +
"\"deliveryTime\" TEXT, " +
"\"receiptTime\" TEXT, " +
"\"cost\" TEXT, " +
"\"prepayment\" TEXT)";*/
