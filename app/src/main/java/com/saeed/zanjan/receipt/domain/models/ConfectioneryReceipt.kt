package com.saeed.zanjan.receipt.domain.models

import androidx.room.ColumnInfo

data class ConfectioneryReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var orderName:String?,
    var orderSpecification:String?,
    var orderWeight:String?,
    var description:String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var cost:String?,
    var prepayment:String?

) {
}