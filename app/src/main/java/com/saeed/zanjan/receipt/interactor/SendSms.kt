package com.saeed.zanjan.receipt.interactor

import android.content.SharedPreferences
import android.telephony.SmsManager
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendSms(
    val sharedPreferences: SharedPreferences
) {



    fun repairSendSMS(
        repairsReceipt: RepairsReceipt

    ): Flow<DataState<String>> = flow {
        emit(DataState.loading())
        val companyName=sharedPreferences.getString("COMPANY","رسید ساز")
        val massageText: String
        try {
            val smsManager = SmsManager.getDefault()
            when (repairsReceipt.status) {
                2 -> massageText =
                    companyName + System.getProperty("line.separator") + "با سلام.مشتری عزیز" + repairsReceipt.name + " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " + "companyPhone" + "تماس بگیرید."

                3 -> massageText =
                    companyName + System.getProperty("line.separator") + "با سلام.مشتری عزیز  " + repairsReceipt.name + "  سفارش شما آماده تحویل است ."

                else -> {
                    massageText =
                        "نام کالا:" + repairsReceipt.loanerName + System.getProperty("line.separator") + "ایرادات:" + repairsReceipt.loanerProblems + System.getProperty(
                            "line.separator"
                        ) + "خطرات:" + repairsReceipt.risks + System.getProperty("line.separator") + "موعد تحویل:" + repairsReceipt.deliveryTime + System.getProperty(
                            "line.separator"
                        ) + "لوازم همراه:" + repairsReceipt.accessories + System.getProperty("line.separator") + "جمع هزینه:" + repairsReceipt.cost + System.getProperty(
                            "line.separator"
                        ) + "پرداخت شده:" + repairsReceipt.prepayment;
                    smsManager.sendTextMessage(
                        repairsReceipt.phone,
                        null,
                        companyName + ":" + System.getProperty("line.separator") + "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                        null,
                        null
                    )
                }
            }
            val parts = smsManager.divideMessage(massageText)
            smsManager.sendMultipartTextMessage(
                repairsReceipt.phone,
                null,
                parts,
                null,
                null
            )
            emit(DataState.success("پیامک ارسال شد"))
        } catch (e: Exception) {
            emit(DataState.error("پیامک ارسال نشد"))
        }



    }


}