package com.saeed.zanjan.receipt.presentation.ui.create_receipt

import android.content.SharedPreferences
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import com.saeed.zanjan.receipt.interactor.ReceiptQueryInDatabase
import com.saeed.zanjan.receipt.interactor.SendSms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateReceiptViewModel
@Inject constructor(
    val receiptQueryInDatabase: ReceiptQueryInDatabase,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val receiptCategory = mutableStateOf(-1)



    val loading = mutableStateOf(false)
    val dataSaveStatus = mutableStateOf(false)
    val savedReceiptId= mutableStateOf(-1L)

    fun getDataFromSharedPreferences(){

         receiptCategory.value=sharedPreferences.getInt("JOB_SUBJECT",-1)
    }

    fun saveInDatabase(
        generalReceipt: GeneralReceipt,
        snackbarHostState: SnackbarHostState,
    ) {
        receiptQueryInDatabase.saveRepairReceipt(generalReceipt,receiptCategory.value).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                savedReceiptId.value=it
                dataSaveStatus.value = true
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)
            }

        }.launchIn(viewModelScope)


    }




fun restartState(){
    dataSaveStatus.value=false
}


}