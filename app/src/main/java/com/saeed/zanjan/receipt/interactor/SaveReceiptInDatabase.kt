package com.saeed.zanjan.receipt.interactor

import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntityMapper
import com.saeed.zanjan.receipt.cash.model.EntitiesGeneralMapper
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
   val generalMapper: EntitiesGeneralMapper
) {




    fun saveRepairReceipt(
        generalReceipt: GeneralReceipt,
        receiptCategory:Int
    ):Flow<DataState<Long>> = flow {
        emit(DataState.loading())
        try {
            var result=-1L
            when (receiptCategory) {
                0 -> {
                   //repair
                    result=receiptDao.insertRepairReceipt(generalMapper.mapToRepairsEntity(generalReceipt))

                }

                1 -> {
                  //repair
                    result=receiptDao.insertRepairReceipt(generalMapper.mapToRepairsEntity(generalReceipt))

                }

                2 -> {
                 //  repair
                    result=receiptDao.insertRepairReceipt(generalMapper.mapToRepairsEntity(generalReceipt))

                }

                3 -> {
                   //tailoring
                    result=receiptDao.insertTailoringReceipt(generalMapper.mapToTailoringEntity(generalReceipt))

                }

                4 -> {
                    //jewelry
                    result=receiptDao.insertJewelryReceipt(generalMapper.mapToJewelryEntity(generalReceipt))

                }

                5 -> {
                   //photo
                    result=receiptDao.insertPhotoReceipt(generalMapper.mapToPhotographyEntity(generalReceipt))

                }

                6 -> {
                   //laundry
                    result=receiptDao.insertLaundryReceipt(generalMapper.mapLaundryEntity(generalReceipt))

                }

                7 -> {
                   //confectionery
                    result=receiptDao.insertConfectioneryReceipt(generalMapper.mapToConfectioneryEntity(generalReceipt))

                }

                8 -> {
                   //otherJobs
                    result=receiptDao.insertOtherJobsReceipt(generalMapper.mapToOtherJobsEntity(generalReceipt))

                }

            }

            if(result>0)
            emit(DataState.success(result))
            else
                emit(DataState.error("خطای ثبت اطلاعات"))


        }catch (e:Exception){
            emit(DataState.error("خطای ناشناخته"))
        }



    }


}