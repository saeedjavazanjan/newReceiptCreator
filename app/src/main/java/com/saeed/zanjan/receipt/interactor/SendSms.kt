package com.saeed.zanjan.receipt.interactor

import android.content.SharedPreferences
import android.telephony.SmsManager
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendSms(
    val sharedPreferences: SharedPreferences
) {



    fun repairSendSMS(
        generalReceipt: GeneralReceipt,
        receiptCategory:Int

    ): Flow<DataState<String>> = flow {
        emit(DataState.loading())
        val companyName=sharedPreferences.getString("COMPANY","")
        val companyPhone=sharedPreferences.getString("PHONE","")
        val link=sharedPreferences.getString("LINK","")

        val smsManager = SmsManager.getDefault()
        var result=0
        when (receiptCategory) {
                0 -> {
                    //repair
                    result=repairSmsPattern(
                        generalReceipt,
                        companyName,
                        smsManager,
                        companyPhone,
                        link
                    )
                }

                1 -> {
                    //repair
                    result=repairSmsPattern(
                        generalReceipt,
                        companyName,
                        smsManager,
                        companyPhone,
                        link
                    )
                }

                2 -> {
                    //  repair
                    result=repairSmsPattern(
                        generalReceipt,
                        companyName,
                        smsManager,
                        companyPhone,
                        link
                    )
                }

                3 -> {
                    //tailoring
                    result=tailoringSmsPattern(
                        generalReceipt,
                        companyName,
                        companyPhone,
                        smsManager,
                        link
                    )

                }

                4 -> {
                    //jewelry
                    result=jewelrySmsPattern(
                        generalReceipt,
                        companyName,
                        companyPhone,
                        smsManager,
                        link
                    )
                }

                5 -> {
                    //photo
                    result=photographySmsPattern(
                        generalReceipt,
                        companyName,
                        companyPhone,
                        smsManager,
                        link
                    )
                }

                6 -> {
                    //laundry
                    result=laundrySmsPattern(
                        generalReceipt,
                        companyName,
                        companyPhone,
                        smsManager,
                        link
                    )
                }

                7 -> {
                    //confectionery
                    result=confectionerySmsPattern(
                        generalReceipt,
                        companyName,
                        companyPhone,
                        smsManager,
                        link
                    )
                }

                8 -> {
                    //otherJobs
                    result=otherJobsSmsPattern(
                        generalReceipt,
                        companyName,
                        companyPhone,
                        smsManager,
                        link
                    )

                }

            }

        if(result==1){
            emit(DataState.success("پیامک ارسال شد"))

        }else{
            emit(DataState.error("خطا در ارسال پیامک"))
        }




    }


    private fun repairSmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        smsManager:SmsManager,
        companyPhone: String?,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName + System.getProperty("line.separator") + "با سلام.مشتری عزیز" +
                             generalReceipt.name + " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " +
                             companyPhone + "تماس بگیرید."+
                             System.getProperty("line.separator")+
                             link

                 3 -> massageText =
                     companyName + System.getProperty("line.separator") + "با سلام.مشتری عزیز  " +
                             generalReceipt.name + "  سفارش شما آماده تحویل است ."+
                             System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                         "نام کالا:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "ایرادات:" + generalReceipt.repairLoanerProblems + System.getProperty("line.separator") +
                                 "خطرات:" + generalReceipt.repairRisks + System.getProperty("line.separator") +
                                 "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator")+
                                 "لوازم همراه:" + generalReceipt.repairAccessories + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                                 link
                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }
             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }
    private fun confectionerySmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        companyPhone:String?,
        smsManager:SmsManager,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName + System.getProperty("line.separator") + "با سلام.مشتری عزیز" +
                             generalReceipt.name + " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " +
                             companyPhone + "تماس بگیرید."+ System.getProperty("line.separator") +
                             link

                 3 -> massageText =
                     companyName + System.getProperty("line.separator") + "با سلام.مشتری عزیز  " +
                             generalReceipt.name + "  سفارش شما آماده تحویل است ."+
                             System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                                 "نام سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "مشخصات:" + generalReceipt.confectioneryOrderSpecification + System.getProperty("line.separator") +
                                 "توضیحات:" + generalReceipt.confectioneryDescription + System.getProperty("line.separator") +
                                 "وزن:" + generalReceipt.confectioneryOrderWeight + System.getProperty("line.separator") +
                                 "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                                 link
                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }
             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }
    private fun jewelrySmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        companyPhone:String?,
        smsManager:SmsManager,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName + System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز" + generalReceipt.name +
                             " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " +
                             companyPhone + "تماس بگیرید."+ System.getProperty("line.separator") +
                             link

                 3 -> massageText =
                     companyName + System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز  " + generalReceipt.name +
                             "  سفارش شما آماده تحویل است ."+ System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                                 "نام سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "مشخصات کالا:" + generalReceipt.jewelryLoanerSpecification + System.getProperty("line.separator") +
                                 "مشخصات سفارش:" + generalReceipt.jewelryOrderSpecification + System.getProperty("line.separator") +
                                 "مشکلات:" + generalReceipt.jewelryLoanerProblems + System.getProperty("line.separator") +
                                 "موعد تحویل:" +generalReceipt.deliveryTime  + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                                 link
                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }

             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }
 private fun laundrySmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        companyPhone:String?,
        smsManager:SmsManager,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName +
                             System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز" + generalReceipt.name +
                             " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره "+
                             companyPhone + "تماس بگیرید."+ System.getProperty("line.separator") +
                         link

                 3 -> massageText =
                     companyName +
                             System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز  " + generalReceipt.name +
                             "  سفارش شما آماده تحویل است ."+
                             System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                                 "نام کالا:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "نوع سفارش:" + generalReceipt.laundryOrderType + System.getProperty("line.separator") +
                                 "توضیحات:" + generalReceipt.laundryDescription + System.getProperty("line.separator") +
                                 "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                                 link
                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }
             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }
    private fun otherJobsSmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        companyPhone:String?,
        smsManager:SmsManager,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName +
                             System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز" + generalReceipt.name +
                             " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " +
                             companyPhone + "تماس بگیرید."+ System.getProperty("line.separator") +
                             link

                 3 -> massageText =
                     companyName +
                             System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز  " + generalReceipt.name +
                             "  سفارش شما آماده تحویل است ."+
                             System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                                 "عنوان سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "تعداد:" + generalReceipt.otherJobsOrderNumber + System.getProperty("line.separator") +
                                 "توضیحات:" + generalReceipt.otherJobsDescription + System.getProperty("line.separator") +
                                 "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                                 link

                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }
             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }
    private fun photographySmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        companyPhone:String?,
        smsManager:SmsManager,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName +
                             System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز" + generalReceipt.name +
                             " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " +
                             companyPhone + "تماس بگیرید."+ System.getProperty("line.separator") +
                             link

                 3 -> massageText =
                     companyName +
                             System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز  " + generalReceipt.name +
                             "  سفارش شما آماده تحویل است ."+ System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                                 "عنوان سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "تعداد:" + generalReceipt.photographyOrderNumber + System.getProperty("line.separator") +
                                 "اندازه :" + generalReceipt.photographyOrderSize + System.getProperty("line.separator") +
                                 "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:"+ generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                                 link

                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }
             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }
  private fun tailoringSmsPattern(
        generalReceipt: GeneralReceipt,
        companyName:String?,
        companyPhone:String?,
        smsManager:SmsManager,
        link:String?

    ):Int{
        val massageText: String
         try {
             when (generalReceipt.status) {
                 2 -> massageText =
                     companyName +
                             System.getProperty("line.separator") + "با سلام.مشتری عزیز" +
                             generalReceipt.name +
                             " در روند سفارش شما مشکلی به وجود آمده جهت اطلاعات بیشتر با شماره " +
                             companyPhone + "تماس بگیرید."+ System.getProperty("line.separator") +
                             link

                 3 -> massageText =
                     companyName + System.getProperty("line.separator") +
                             "با سلام.مشتری عزیز  " + generalReceipt.name +
                             "  سفارش شما آماده تحویل است ."+ System.getProperty("line.separator") +
                             link

                 else -> {
                     massageText =
                         companyName+ System.getProperty("line.separator") +
                                 "نام سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                                 "مشخصات:" + generalReceipt.tailoringOrderSpecification + System.getProperty("line.separator") +
                                 "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                                 System.getProperty("line.separator") +
                                 "جمع هزینه:" + generalReceipt.cost+" تومان" + System.getProperty("line.separator") +
                                 "پرداخت شده:" + generalReceipt.prepayment+ " تومان" +System.getProperty("line.separator") +
                                 link


                     smsManager.sendTextMessage(
                         generalReceipt.phone,
                         null,
                         "مشتری عزیز:"+ generalReceipt.name + System.getProperty("line.separator") +
                                 "درصورت صحت اطلاعات زیر عدد 1 را ارسال نمایید",
                         null,
                         null
                     )
                 }
             }
             val parts = smsManager.divideMessage(massageText)
             smsManager.sendMultipartTextMessage(
                 generalReceipt.phone,
                 null,
                 parts,
                 null,
                 null
             )
             return 1
          }catch (e:Exception){
              return -1
          }

    }



}