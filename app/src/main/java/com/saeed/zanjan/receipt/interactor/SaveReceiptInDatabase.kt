package com.saeed.zanjan.receipt.interactor

import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntityMapper
import com.saeed.zanjan.receipt.cash.model.JewelryEntityMapper
import com.saeed.zanjan.receipt.cash.model.LaundryEntityMapper
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntityMapper
import com.saeed.zanjan.receipt.cash.model.PhotographyEntityMapper
import com.saeed.zanjan.receipt.cash.model.RepairsEntityMapper
import com.saeed.zanjan.receipt.cash.model.TailoringEntityMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveReceiptInDatabase(
    val receiptDao: ReceiptDao,
    private val conEntityMapper:ConfectioneryEntityMapper,
    private val jewEntityMapper:JewelryEntityMapper,
    private val laEntityMapper:LaundryEntityMapper,
    private val otherEntityMapper:OtherJobsEntityMapper,
    private val photoEntityMapper:PhotographyEntityMapper,
    private val tailorEntityMapper:TailoringEntityMapper,
    private val repairEntityMapper:RepairsEntityMapper,
) {




    fun saveRepairReceipt(
        generalReceipt: GeneralReceipt,
        receiptCategory:Int
    ):Flow<DataState<String>> = flow {
        emit(DataState.loading())
        try {
            var result=-1L
            when (receiptCategory) {
                0 -> {
                   //repair
                    result=receiptDao.insertRepairReceipt(repairEntityMapper.generalMapper(generalReceipt))

                }

                1 -> {
                  //repair
                    result=receiptDao.insertRepairReceipt(repairEntityMapper.generalMapper(generalReceipt))

                }

                2 -> {
                 //  repair
                    result=receiptDao.insertRepairReceipt(repairEntityMapper.generalMapper(generalReceipt))

                }

                3 -> {
                   //tailoring
                    result=receiptDao.insertTailoringReceipt(tailorEntityMapper.generalMapper(generalReceipt))

                }

                4 -> {
                    //jewelry
                    result=receiptDao.insertJewelryReceipt(jewEntityMapper.generalMapper(generalReceipt))

                }

                5 -> {
                   //photo
                    result=receiptDao.insertPhotoReceipt(photoEntityMapper.generalMapper(generalReceipt))

                }

                6 -> {
                   //laundry
                    result=receiptDao.insertLaundryReceipt(laEntityMapper.generalMapper(generalReceipt))

                }

                7 -> {
                   //confectionery
                    result=receiptDao.insertConfectioneryReceipt(conEntityMapper.generalMapper(generalReceipt))

                }

                8 -> {
                   //otherJobs
                    result=receiptDao.insertOtherJobsReceipt(otherEntityMapper.generalMapper(generalReceipt))

                }

            }

            if(result>0)
            emit(DataState.success("با موفقیت ذخیره شد"))
            else
                emit(DataState.error("خطای ثبت اطلاعات"))


        }catch (e:Exception){
            emit(DataState.error("خطای ناشناخته"))
        }



    }


}