package com.saeed.zanjan.receipt.domain.models


data class TailoringReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var loanerName:String?,
    var orderSpecification:String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var sizes:String?,
    var cost:String?,
    var prepayment:String?
)