package com.saeed.zanjan.receipt.presentation.ui.customers_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt

class CustomersListViewModel:ViewModel() {

    val receiptCategory = 1//sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    val customersList: MutableState<List<Customer>> = mutableStateOf(ArrayList())






    fun getListOfCustomers(snackbarHostState: SnackbarHostState){


    }

    fun searchCustomer(query:String,snackbarHostState: SnackbarHostState){


    }
}