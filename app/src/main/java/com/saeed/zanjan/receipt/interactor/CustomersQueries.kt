package com.saeed.zanjan.receipt.interactor

import android.annotation.SuppressLint
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.CustomerEntityMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query

class CustomersQueries(
    private val receiptDao: ReceiptDao,
    private val customerEntityMapper: CustomerEntityMapper
) {





    @SuppressLint("SuspiciousIndentation")
    fun getListOfCustomers():Flow<DataState<List<Customer>>> = flow {
        emit(DataState.loading())
        try {
          val result=  receiptDao.getAllCustomers()
            emit(DataState.success(customerEntityMapper.mapToDomainList(result)))

        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))
        }

    }

    fun deleteCustomer(
        id:Int
    ):Flow<DataState<String>> = flow {
        emit(DataState.loading())
        try {
            val result=  receiptDao.deleteCustomer(id)
            if(result>0){
                emit(DataState.success("حذف موفق از لیست مشتریان"))

            } else {
                emit(DataState.error("خطا در حذف اطلاعات"))
            }

        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))
        }

    }


    fun  searchCustomer(
        query: String
    ):Flow<DataState<List<Customer>>> = flow {
        emit(DataState.loading())
        try {
                val result=receiptDao.searchCustomer(query)
                emit(DataState.success(customerEntityMapper.mapToDomainList(result)))

        }catch (e:Exception){
            emit(DataState.error(e.message.toString()))
        }
    }


}