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
                    result.forEach {repair->
                        list.add(generalMapper.mapFromRepairsEntity(repair))
                    }
                    emit(DataState.success(list))
                }

                1 -> {
                    //repair
                    val result=receiptDao.getAllRepairsEntity()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {repair->
                        list.add(generalMapper.mapFromRepairsEntity(repair))
                    }
                    emit(DataState.success(list))
                }

                2 -> {
                    //  repair
                    val result=receiptDao.getAllRepairsEntity()
                    val list= mutableListOf<GeneralReceipt>()
                    result.forEach {repair->
                        list.add(generalMapper.mapFromRepairsEntity(repair))
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

            }

        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))


        }




    }




}