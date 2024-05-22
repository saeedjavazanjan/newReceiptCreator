package com.saeed.zanjan.receipt.domain.models

import androidx.room.ColumnInfo

data class OtherJobsReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var orderName: String?,
    var description: String?,
    var orderNumber: String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var cost:String?,
    var prepayment:String?

) {
}