package com.saeed.zanjan.receipt.interactor

import android.provider.ContactsContract.Data
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.EntitiesGeneralMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListOfReceipts(
    val receiptDao: ReceiptDao,
    val generalMapper: EntitiesGeneralMapper
)
{


    fun getListOfReceipts(

        receiptCategory: Int
    ): Flow<DataState<List<GeneralReceipt>>> = flow {
        emit(DataState.loading())
        try {
            when (receiptCategory) {
                0 -> {
                    //repair
                   val result=receiptDao.getAllRepairsEntity()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromRepairsEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                1 -> {
                    //repair
                    val result=receiptDao.getAllRepairsEntity()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromRepairsEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                2 -> {
                    //  repair
                    val result=receiptDao.getAllRepairsEntity()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromRepairsEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                3 -> {
                    //tailoring
                    val result=receiptDao.getAllTailoringReceipts()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {item->
                        list.add(generalMapper.mapFromTailoringEntity(item))
                    }
                    emit(DataState.success(list))
                }

                4 -> {
                    //jewelry
                    val result=receiptDao.getAllJewelryReceipts()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {item->
                        list.add(generalMapper.mapFromJewelryEntity(item))
                    }
                    emit(DataState.success(list))
                }

                5 -> {
                    //photo
                    val result=receiptDao.getAllPhotographyReceipts()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {item->
                        list.add(generalMapper.mapFromPhotographyEntity(item))
                    }
                    emit(DataState.success(list))
                }

                6 -> {
                    //laundry
                    val result=receiptDao.getAllLaundryReceipts()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {item->
                        list.add(generalMapper.mapFromLaundryEntity(item))
                    }
                    emit(DataState.success(list))
                }

                7 -> {
                    //confectionery
                    val result=receiptDao.getAllConfectioneryReceipts()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {item->
                        list.add(generalMapper.mapFromConfectioneryEntity(item))
                    }
                    emit(DataState.success(list))
                }

                8 -> {
                    //otherJobs
                    val result=receiptDao.getAllOtherJobsReceipts()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {item->
                        list.add(generalMapper.mapFromOtherJobsEntity(item))
                    }
                    emit(DataState.success(list))
                }
                else->{
                    emit(DataState.error("خطای دریافت دسته بندی"))
                }

            }

        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))


        }




    }


    fun searchReceipt(
        query:String,
        receiptCategory: Int
    ):Flow<DataState<List<GeneralReceipt>>> = flow{
        emit(DataState.loading())
        try {
            when (receiptCategory) {
                0 -> {
                    //repair
                    val result=receiptDao.searchRepairs(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromRepairsEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                1 -> {
                    //repair
                    val result=receiptDao.searchRepairs(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromRepairsEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                2 -> {
                    //  repair
                    val result=receiptDao.searchRepairs(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromRepairsEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                3 -> {
                    //tailoring
                    val result=receiptDao.searchTailoring(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromTailoringEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                4 -> {
                    //jewelry
                    val result=receiptDao.searchJewelry(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromJewelryEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                5 -> {
                    //photo
                    val result=receiptDao.searchPhotography(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromPhotographyEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                6 -> {
                    //laundry
                    val result=receiptDao.searchLaundry(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromLaundryEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                7 -> {
                    //confectionery
                    val result=receiptDao.searchConfectioneries(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromConfectioneryEntity(rec))
                    }
                    emit(DataState.success(list))
                }

                8 -> {
                    //otherJobs
                    val result=receiptDao.searchOtherJobs(query)
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {rec->
                        list.add(generalMapper.mapFromOtherJobsEntity(rec))
                    }
                    emit(DataState.success(list))
                }
                else->{
                    emit(DataState.error("خطای دریافت دسته بندی"))
                }

            }
        }catch (e:Exception){
            emit(DataState.error(e.message.toString()?:"خطای ناشناخته"))

        }



    }


    fun getReceiptById(
        receiptId:Int,
        receiptCategory: Int
    ):Flow<DataState<GeneralReceipt>> = flow {
        emit(DataState.loading())
        try {
            when (receiptCategory) {
                0 -> {
                    //repair
                    val result=receiptDao.getRepairsReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromRepairsEntity(result)))
                }

                1 -> {
                    //repair
                    val result=receiptDao.getRepairsReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromRepairsEntity(result)))
                }

                2 -> {
                    //  repair
                    val result=receiptDao.getRepairsReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromRepairsEntity(result)))
                }

                3 -> {
                    //tailoring
                    val result=receiptDao.getTailoringReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromTailoringEntity(result)))
                }

                4 -> {
                    //jewelry
                    val result=receiptDao.getJewelryReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromJewelryEntity(result)))
                }

                5 -> {
                    //photo
                    val result=receiptDao.getPhotographyReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromPhotographyEntity(result)))
                }

                6 -> {
                    //laundry
                    val result=receiptDao.getLaundryReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromLaundryEntity(result)))
                }

                7 -> {
                    //confectionery
                    val result=receiptDao.getConfectioneryReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromConfectioneryEntity(result)))
                }

                8 -> {
                    //otherJobs
                    val result=receiptDao.getOtherJobsReceipt(receiptId)
                    emit(DataState.success(generalMapper.mapFromOtherJobsEntity(result)))
                }
                else->{
                    emit(DataState.error("خطای دریافت دسته بندی"))
                }

            }
        }catch (e:Exception){
            emit(DataState.error(e.message.toString()?:"خطای ناشناخته"))

        }


    }



}