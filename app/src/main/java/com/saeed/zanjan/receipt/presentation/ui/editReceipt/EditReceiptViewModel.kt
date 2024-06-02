package com.saeed.zanjan.receipt.presentation.ui.editReceipt

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditReceiptViewModel
@Inject constructor(
    val receiptQueryInDatabase: ReceiptQueryInDatabase,
    sharedPreferences: SharedPreferences,
    val listOfReceipts: ListOfReceipts
) : ViewModel() {

    val receiptCategory=1//sharedPreferences.getInt("JOB_SUBJECT",-1)

    val loading = mutableStateOf(false)
    val dataSaveStatus = mutableStateOf(false)

    private val _currentReceipt = MutableStateFlow(GeneralReceipt())
    val currentReceipt: StateFlow<GeneralReceipt> = _currentReceipt.asStateFlow()
    fun updateReceipt(
        generalReceipt: GeneralReceipt,
        snackbarHostState: SnackbarHostState,
    ) {
        receiptQueryInDatabase.updateReceipt(generalReceipt,receiptCategory).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                dataSaveStatus.value = true
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)
            }

        }.launchIn(viewModelScope)


    }



    fun getReceiptById(
        receiptId:Int,
        snackbarHostState: SnackbarHostState

    ){

        listOfReceipts.getReceiptById(
            receiptId,
            receiptCategory
        ).onEach {dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                _currentReceipt.value=it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }


        }.catch {
            loading.value=false
            snackbarHostState.showSnackbar(it.message.toString()?:"خطای ناشناخته")
        }.launchIn(viewModelScope)


    }


    fun restartState(){
        dataSaveStatus.value=false
    }

    fun restartCurrentReceipt(){
        _currentReceipt.value= GeneralReceipt()

    }
}