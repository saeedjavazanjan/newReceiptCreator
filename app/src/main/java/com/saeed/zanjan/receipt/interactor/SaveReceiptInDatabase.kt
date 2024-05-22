package com.saeed.zanjan.receipt.interactor

import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.RepairsEntityMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveReceiptInDatabase(
    val receiptDao: ReceiptDao,
    val entityMapper:RepairsEntityMapper
) {




    fun saveRepairReceipt(
        repairsReceipt: RepairsReceipt
    ):Flow<DataState<String>> = flow {
        emit(DataState.loading())

        try {
            val result=receiptDao.insertRepairReceipt(entityMapper.mapFromDomainModel(repairsReceipt))
            if(result>0)
            emit(DataState.success("با موفقیت ذخیره شد"))
            else
                emit(DataState.error("خطای ثبت اطلاعات"))


        }catch (e:Exception){
            emit(DataState.error("خطای ناشناخته"))
        }



    }


}