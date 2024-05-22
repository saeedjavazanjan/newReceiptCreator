package com.saeed.zanjan.receipt.domain.models

import androidx.room.ColumnInfo

data class LaundryReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var loanerName:String?,
    var orderType:String?,
    var description:String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var cost:String?,
    var prepayment:String?

) {
}