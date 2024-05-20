package com.saeed.zanjan.receipt.cash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repairs_new")

data class RepairsEntity(
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

    @ColumnInfo(name = "loanerProblems")
    var loanerProblems:String?,

    @ColumnInfo(name = "Risks")
    var risks:String?,

  @ColumnInfo(name = "deliveryTime")
    var deliveryTime:String?,

  @ColumnInfo(name = "receiptTime")
    var receiptTime:String?,

  @ColumnInfo(name = "accessories")
    var accessories:String?,

    @ColumnInfo(name = "cost")
    var cost:String?,

    @ColumnInfo(name = "prepayment")
    var prepayment:String?

)