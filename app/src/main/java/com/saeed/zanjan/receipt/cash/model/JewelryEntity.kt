package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jewelry")

data class JewelryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "status")
    var status: Int?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "phone")
    var phone: String?,

    @ColumnInfo(name = "loanerName")
    var loanerName: String?,

    @ColumnInfo(name = "OrderSpecification")
    var orderSpecification: String?,

    @ColumnInfo(name = "loanerProblems")
    var loanerProblems: String?,

    @ColumnInfo(name = "loanerSpecification")
    var loanerSpecification: String?,

    @ColumnInfo(name = "deliveryTime")
    var deliveryTime: String?,

    @ColumnInfo(name = "receiptTime")
    var receiptTime: String?,

    @ColumnInfo(name = "cost")
    var cost: String?,

    @ColumnInfo(name = "prepayment")
    var prepayment: String?

)
/*jewelry = "CREATE  TABLE  IF NOT EXISTS \"jewelry\" (" +
"\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
"\"status\" INTEGER , " +
"\"name\" TEXT NOT NULL   , " +
"\"phone\" TEXT, " +
"\"loanerName\" TEXT, " +
"\"OrderSpecification\" TEXT, " +
"\"loanerProblems\" TEXT, " +
"\"loanerSpecification\" TEXT, " +
"\"deliveryTime\" TEXT, " +
"\"receiptTime\" TEXT, " +
"\"cost\" TEXT, " +
"\"prepayment\" TEXT)";*/
