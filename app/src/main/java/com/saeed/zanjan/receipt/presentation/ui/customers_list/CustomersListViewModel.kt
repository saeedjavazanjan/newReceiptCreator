package com.saeed.zanjan.receipt.presentation.ui.customers_list

import android.content.SharedPreferences
import android.telephony.SmsMessage
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.CustomersQueries
import com.saeed.zanjan.receipt.interactor.SendSms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel
   @Inject constructor(
       private val customersQueries: CustomersQueries,
       private val sendSms: SendSms,
       private val sharedPreferences: SharedPreferences
   ):ViewModel() {

    val receiptCategory = sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    var customersList= mutableStateOf( mutableListOf<Customer>())






    fun getListOfCustomers(snackbarHostState: SnackbarHostState){
        customersQueries.getListOfCustomers().onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                customersList.value= it.toMutableList()
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)


    }

    fun searchCustomer(query:String,snackbarHostState: SnackbarHostState){

        customersQueries.searchCustomer(query).onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                customersList.value.clear()
                customersList.value.addAll(it)
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)


    }

    fun deleteCustomer(id:Int,snackbarHostState: SnackbarHostState){
        customersQueries.deleteCustomer(id).onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar(it)
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)
    }


    fun sendSmsToCustomers(message: String,destinations:List<String>, snackbarHostState: SnackbarHostState){
        sendSms.sendCustomTextSMS(
            destinations,
            message
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
        dataState.data?.let {
            snackbarHostState.showSnackbar(it)
        }
        dataState.error?.let {
            snackbarHostState.showSnackbar(it)

        }

        }.launchIn(viewModelScope)
    }


    fun filterCustomers(snackbarHostState: SnackbarHostState){
       var indebtedCustomers= mutableListOf<Customer>()
        customersList.value.forEach {cus->
            if(cus.dept!! > 0){
                indebtedCustomers.add(cus)
            }
            customersList.value=indebtedCustomers
        }


    }
    fun restartState(){
        customersList.value= mutableListOf()

    }
}