package com.saeed.zanjan.receipt.bazar

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.compose.runtime.mutableStateOf
import ir.cafebazaar.poolakey.Payment
import ir.cafebazaar.poolakey.config.PaymentConfiguration
import ir.cafebazaar.poolakey.config.SecurityCheck
import ir.cafebazaar.poolakey.entity.PurchaseInfo
import ir.cafebazaar.poolakey.request.PurchaseRequest

class BazarInAppBill {
    val localSecurityCheck = SecurityCheck.Enable(
        rsaPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDMktQ+9MJj6kJYY+pf+X4RPcvdLOjy3HG0BZH8Pkqj8on5yc+k2xzpvRg71fR4pzoNcNXHmVIbqefoRxraTu9IIWJ/YQauSix9v2VrKqdiYPCYlGbyD8NnryJXykHtRYeAeuJVseWLwPS/SAlkkVTY5ooQLXX+O7ZJS1ungbi/lkd3AkeRzYi9YZ1oI392T3hxPkoFhaWq4w/TcV7lpOS/f1PCfsy1Z2IKyvZOx88CAwEAAQ=="
    )
    val paymentConfiguration = PaymentConfiguration(
        localSecurityCheck = localSecurityCheck
    )

    lateinit var payment:Payment


    var userPurchaseInfo= mutableListOf<UserPurchaseInfo>()
    fun connectToBazar(context: Context){
         payment = Payment(context = context, config = paymentConfiguration)
        val paymentConnection = payment.connect {
            connectionSucceed {
            }
            connectionFailed { throwable ->
                Toast.makeText(context,throwable.message.toString(),Toast.LENGTH_SHORT).show()
            }
            disconnected {
                Toast.makeText(context,"ارتباط با بازار قطع شد",Toast.LENGTH_SHORT).show()

            }
        }


    }

    fun buySubscribe(
        activityResultRegistry:ActivityResultRegistry,
        productID:String,
        payload:String,
        context: Context
    ){
        val purchaseRequest = PurchaseRequest(
            productId =productID,
            payload = payload
        )

        payment.subscribeProduct(
            registry = activityResultRegistry,
            request = purchaseRequest
        ) {
            purchaseFlowBegan {
                Toast.makeText(context,"ارتباط با بازار...",Toast.LENGTH_SHORT).show()

            }
            failedToBeginFlow { throwable ->
                Toast.makeText(context,throwable.message.toString(),Toast.LENGTH_SHORT).show()

            }
            purchaseSucceed { purchaseEntity ->
                Toast.makeText(context,"خرید با موفقیت انجام شد",Toast.LENGTH_SHORT).show()

            }
            purchaseCanceled {
                Toast.makeText(context,"خرید لغو شد",Toast.LENGTH_SHORT).show()
            }
            purchaseFailed { throwable ->
                Toast.makeText(context,throwable.message.toString(),Toast.LENGTH_SHORT).show()

            }
        }

    }



    fun getUserSubscribes(context: Context) {
        payment.getSubscribedProducts {
            querySucceed { purchasedProducts ->
                Log.i("BAZARLog",purchasedProducts.toString())

                purchasedProducts.forEach {pInfo->
                Log.i("BAZARLog",pInfo.purchaseTime.toString())
                    userPurchaseInfo.add(
                        UserPurchaseInfo(
                            orderId = pInfo.orderId,
                            purchaseToken = pInfo.purchaseToken,
                            payload = pInfo.payload,
                            packageName = pInfo.packageName,
                            purchaseState = pInfo.purchaseState,
                            purchaseTime = pInfo.purchaseTime,
                            productId = pInfo.productId,
                            originalJson = pInfo.originalJson,
                            dataSignature = pInfo.dataSignature
                        )
                    )
                }



            }
            queryFailed { throwable ->
                Toast.makeText(context,throwable.message.toString(),Toast.LENGTH_SHORT).show()
                Log.i("BAZAR","faild")

            }
        }
    }


}