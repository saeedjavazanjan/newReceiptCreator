package com.saeed.zanjan.receipt.interactor

import android.provider.ContactsContract.Data
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListOfReceipts(
    receiptDao: ReceiptDao
)
{


    fun getListOfReceipts(

        receiptCategory: Int
    ): Flow<DataState<GeneralReceipt>> = flow {
        emit(DataState.loading())
        try {

            rec

        }catch (e:Exception){



        }




    }




}