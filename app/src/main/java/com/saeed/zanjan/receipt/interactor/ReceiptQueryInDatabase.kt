package com.saeed.zanjan.receipt.interactor

import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.CustomerEntity
import com.saeed.zanjan.receipt.cash.model.EntitiesGeneralMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReceiptQueryInDatabase(
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
            var insertCustomerResult=-1L
            val customerEntity=CustomerEntity(
                id=0,
                name = generalReceipt.name,
                phoneNumber = generalReceipt.phone,
                payedAmount = generalReceipt.prepayment,
                totalAmount = generalReceipt.cost
            )
            val checkRepeatOfCustomer=receiptDao.countByPhoneNumber(generalReceipt.phone!!)
            when (receiptCategory) {
                0 -> {
                   //repair
                    result=receiptDao.insertRepairReceipt(generalMapper.mapToRepairsEntity(generalReceipt))

                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)

                }

                1 -> {
                  //repair
                    result=receiptDao.insertRepairReceipt(generalMapper.mapToRepairsEntity(generalReceipt))

                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                2 -> {
                 //  repair
                    result=receiptDao.insertRepairReceipt(generalMapper.mapToRepairsEntity(generalReceipt))

                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                3 -> {
                   //tailoring
                    result=receiptDao.insertTailoringReceipt(generalMapper.mapToTailoringEntity(generalReceipt))
                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                4 -> {
                    //jewelry
                    result=receiptDao.insertJewelryReceipt(generalMapper.mapToJewelryEntity(generalReceipt))
                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                5 -> {
                   //photo
                    result=receiptDao.insertPhotoReceipt(generalMapper.mapToPhotographyEntity(generalReceipt))
                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                6 -> {
                   //laundry
                    result=receiptDao.insertLaundryReceipt(generalMapper.mapLaundryEntity(generalReceipt))
                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                7 -> {
                   //confectionery
                    result=receiptDao.insertConfectioneryReceipt(generalMapper.mapToConfectioneryEntity(generalReceipt))
                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

                8 -> {
                   //otherJobs
                    result=receiptDao.insertOtherJobsReceipt(generalMapper.mapToOtherJobsEntity(generalReceipt))
                    if (checkRepeatOfCustomer>0)
                        insertCustomerResult=0
                    else
                        insertCustomerResult= receiptDao.insertCustomer(customerEntity)
                }

            }

            if(result>0 && insertCustomerResult>=0)
            emit(DataState.success(result))
            else
                emit(DataState.error("خطای ثبت اطلاعات"))


        }catch (e:Exception){
            emit(DataState.error("خطای ناشناخته"))
        }



    }


    fun updateReceipt(
        generalReceipt: GeneralReceipt,
        receiptCategory: Int
    ):Flow<DataState<String>> = flow {

        emit(DataState.loading())
        try {
            when (receiptCategory) {
                0 -> {
                    //repair
                  receiptDao.updateRepair(generalMapper.mapToRepairsEntity(generalReceipt))

                }

                1 -> {
                    //repair
                   receiptDao.updateRepair(generalMapper.mapToRepairsEntity(generalReceipt))

                }

                2 -> {
                    //  repair
                    receiptDao.updateRepair(generalMapper.mapToRepairsEntity(generalReceipt))

                }

                3 -> {
                    //tailoring
                    receiptDao.updateTailoring(generalMapper.mapToTailoringEntity(generalReceipt))

                }

                4 -> {
                    //jewelry
                    receiptDao.updateJewelry(generalMapper.mapToJewelryEntity(generalReceipt))

                }

                5 -> {
                    //photo
                    receiptDao.updatePhotography(generalMapper.mapToPhotographyEntity(generalReceipt))

                }

                6 -> {
                    //laundry
                 receiptDao.updateLaundry(generalMapper.mapLaundryEntity(generalReceipt))

                }

                7 -> {
                    //confectionery
                    receiptDao.updateConfectionery(generalMapper.mapToConfectioneryEntity(generalReceipt))

                }

                8 -> {
                    //otherJobs
                    receiptDao.updateOtherJobs(generalMapper.mapToOtherJobsEntity(generalReceipt))

                }

            }

                emit(DataState.success("با موفقیت به روز رسانی شد"))



        }catch (e:Exception){
            emit(DataState.error("خطای ناشناخته"))
        }





    }


    fun deleteReceipt(
        receiptId:Int,
        receiptCategory: Int
    ): Flow <DataState<String>> = flow {
        emit(DataState.loading())
        try {
            var result=-1
            when (receiptCategory) {
                0 -> {
                    //repair
                   result= receiptDao.deleteRepair(receiptId)

                }

                1 -> {
                    //repair
                    result= receiptDao.deleteRepair(receiptId)

                }

                2 -> {
                    //  repair
                    result= receiptDao.deleteRepair(receiptId)

                }

                3 -> {
                    //tailoring
                    result= receiptDao.deleteTailoring(receiptId)

                }

                4 -> {
                    //jewelry
                    result= receiptDao.deleteJewelry(receiptId)

                }

                5 -> {
                    //photo
                    result= receiptDao.deletePhotography(receiptId)

                }

                6 -> {
                    //laundry
                    result= receiptDao.deleteLaundry(receiptId)

                }

                7 -> {
                    //confectionery
                    result= receiptDao.deleteConfectionery(receiptId)

                }

                8 -> {
                    //otherJobs
                    result= receiptDao.deleteOtherJobs(receiptId)

                }

            }

            if(result>0)
            emit(DataState.success("با موفقیت حذف شد"))
            else
                emit(DataState.error("خطای نا شناخته"))




        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))
        }



    }
}