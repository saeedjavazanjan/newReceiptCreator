package com.saeed.zanjan.receipt.domain.models

import androidx.room.ColumnInfo

data class RepairsReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var loanerName:String?,
    var loanerProblems:String?,
    var risks:String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var accessories:String?,
    var cost:String?,
    var prepayment:String?

) {
}