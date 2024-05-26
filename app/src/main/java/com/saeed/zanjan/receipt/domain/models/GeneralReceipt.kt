package com.saeed.zanjan.receipt.domain.models

data class GeneralReceipt(
    var id: Int,
    var status:Int? ,
    var name:String ,
    var phone:String?,
    var orderName:String?,
    var deliveryTime:String?,
    var receiptTime:String?,
    var cost:String?,
    var prepayment:String?,

    var confectioneryOrderSpecification:String?,
    var confectioneryOrderWeight:String?,
    var confectioneryDescription:String?,

    var jewelryOrderSpecification: String?,
    var jewelryLoanerProblems: String?,
    var jewelryLoanerSpecification: String?,

    var laundryOrderType:String?,
    var laundryDescription:String?,

    var otherJobsDescription: String?,
    var otherJobsOrderNumber: String?,

    var photographyOrderSize:String?,
    var photographyOrderNumber:String?,

    var repairLoanerProblems:String?,
    var repairRisks:String?,
    var repairAccessories:String?,

    var tailoringOrderSpecification:String?,
    var tailoringSizes:String?,
)
