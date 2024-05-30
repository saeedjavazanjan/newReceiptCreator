package com.saeed.zanjan.receipt.presentation.ui.receipt
import android.content.SharedPreferences
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel
@Inject  constructor(
    val listOfReceipts: ListOfReceipts,
    val sharedPreferences: SharedPreferences
) :ViewModel() {

    val receiptCategory=1//sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)

    val currentReceipt= mutableStateOf(GeneralReceipt())

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
                currentReceipt.value=it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }


        }.catch {
            loading.value=false
            snackbarHostState.showSnackbar(it.message.toString()?:"خطای ناشناخته")
        }.launchIn(viewModelScope)


    }


}