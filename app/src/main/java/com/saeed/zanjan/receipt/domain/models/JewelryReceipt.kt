package com.saeed.zanjan.receipt.domain.models

data class JewelryReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var loanerName:String?,
    var orderSpecification: String?,
    var loanerProblems: String?,
    var loanerSpecification: String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var cost:String?,
    var prepayment:String?

) {
}