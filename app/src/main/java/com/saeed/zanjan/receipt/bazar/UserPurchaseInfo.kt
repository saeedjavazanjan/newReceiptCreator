package com.saeed.zanjan.receipt.bazar

import ir.cafebazaar.poolakey.entity.PurchaseState

data class UserPurchaseInfo(
    val orderId: String,
    val purchaseToken: String,
    val payload: String,
    val packageName: String,
    val purchaseState: PurchaseState,
    val purchaseTime: Long,
    val productId: String,
    val originalJson: String,
    val dataSignature: String
)
